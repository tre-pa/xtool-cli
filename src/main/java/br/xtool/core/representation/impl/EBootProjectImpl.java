package br.xtool.core.representation.impl;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EAppProperties;
import br.xtool.core.representation.EClass;
import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.EPackage;
import br.xtool.core.representation.EPom;
import br.xtool.core.representation.ERepository;
import br.xtool.core.representation.ERest;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.angular.ENgProject;
import br.xtool.core.representation.enums.ProjectType;
import br.xtool.core.util.RoasterUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@Slf4j
public class EBootProjectImpl extends EProjectImpl implements EBootProject {

	private Map<String, JavaUnit> javaUnits;

	private EPom pom;

	private EAppProperties applicationProperties;

	private EClass mainClass;

	public EBootProjectImpl(String path) {
		super(path);
	}

	/**
	 * Retorna o nome da classe base. O nome da classe base é o nome da classe que
	 * possui a annotation @SpringBootApplication sem o sufixo 'Application'
	 * 
	 * @return Nome da classe base.
	 */
	@Override
	public String getBaseClassName() {
		return this.getMainclass().getName().replaceAll("Application", "");
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public EClass getMainclass() {
		if (Objects.isNull(this.mainClass)) {
			// @formatter:off
			this.mainClass = this.getJavaUnits().values()
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isClass())
					.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("SpringBootApplication")))
					.map(javaUnit -> new EClassImpl(this, javaUnit))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Não foi possível localizar a classe principal (@SpringBootApplication). Verifique se a mesma existe ou contêm erros."));
			// @formatter:on
		}
		return this.mainClass;
	}

	/**
	 * Retorna o pacote raiz.
	 * 
	 * @return
	 */
	@Override
	public EPackage getRootPackage() {
		return this.getPom().getGroupId();
	}

	/**
	 * Retorna a representação do arquivo pom.xml
	 * 
	 * @return
	 */
	@Override
	public EPom getPom() {
		if (Objects.isNull(this.pom)) {
			this.pom = EPomImpl.of(FilenameUtils.concat(this.getPath(), "pom.xml"));
		}
		return this.pom;
	}

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	@Override
	public EAppProperties getApplicationProperties() {
		if (Objects.isNull(this.applicationProperties)) {
			this.applicationProperties = EAppPropertiesImpl.of(FilenameUtils.concat(this.getPath(), "src/main/resources/application.properties"));
		}
		return this.applicationProperties;
	}

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	@Override
	public SortedSet<EEntity> getEntities() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
			.map(j -> new EEntityImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	@Override
	public SortedSet<ERepository> getRepositories() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
			.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
			.map(j -> new ERepositoryImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public SortedSet<ERest> getRests() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
			.map(j -> new ERestImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	public Map<String, JavaUnit> getJavaUnits() {
		if (Objects.isNull(this.javaUnits)) {
			// @formatter:off
			this.javaUnits = this.getDirectory().getAllFiles().stream()
				.filter(file -> file.getName().endsWith(".java"))
				.map(RoasterUtil::createJavaUnit)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(javaUnit -> javaUnit.getGoverningType().getName(), Function.identity()));
			// @formatter:on
		}
		return this.javaUnits;
	}

	@Override
	public Optional<ENgProject> getAssociatedAngularProject() {
		String angularPath = this.getPath().replace("-service", "");
		return ENgProjectImpl.of(angularPath);
	}

	@Override
	public String getMainDir() {
		return FilenameUtils.concat(this.getPath(), "src/main/java");
	}

	@Override
	public void refresh() {
		this.javaUnits = null;
	}

	public static Optional<EBootProject> of(String path) {
		if (Stream.of(ProjectType.SPRINGBOOT1_PROJECT, ProjectType.SPRINGBOOT2_PROJECT).anyMatch(p -> p.equals(EDirectoryImpl.of(path).getProjectType()))) {
			EBootProjectImpl project = new EBootProjectImpl(path);
			return Optional.of(project);
		}
		return Optional.empty();
	}
}
