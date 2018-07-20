package br.xtool.core.representation.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaEntity;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJavaRepository;
import br.xtool.core.representation.EJavaRest;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.ESBootAppProperties;
import br.xtool.core.representation.ESBootPom;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.representation.EUmlClassDiagram;
import br.xtool.core.util.RoasterUtil;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class ESBootProjectImpl extends EProjectImpl implements ESBootProject {

	private Map<String, JavaUnit> javaUnits;

	private ESBootPom pom;

	private ESBootAppProperties applicationProperties;

	private EJavaClass mainClass;

	public ESBootProjectImpl(String path) {
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
	public EJavaClass getMainclass() {
		if (Objects.isNull(this.mainClass)) {
			// @formatter:off
			this.mainClass = this.getJavaUnits().values()
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isClass())
					.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("SpringBootApplication")))
					.map(javaUnit -> new EJavaClassImpl(this, javaUnit))
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
	public EJavaPackage getRootPackage() {
		return this.getPom().getGroupId();
	}

	/**
	 * Retorna a representação do arquivo pom.xml
	 * 
	 * @return
	 */
	@Override
	public ESBootPom getPom() {
		if (Objects.isNull(this.pom)) {
			this.pom = ESBootPomImpl.of(FilenameUtils.concat(this.getPath(), "pom.xml"));
		}
		return this.pom;
	}

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	@Override
	public ESBootAppProperties getApplicationProperties() {
		if (Objects.isNull(this.applicationProperties)) {
			this.applicationProperties = ESBootAppPropertiesImpl.of(FilenameUtils.concat(this.getPath(), "src/main/resources/application.properties"));
		}
		return this.applicationProperties;
	}

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	@Override
	public SortedSet<EJavaEntity> getEntities() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
			.map(j -> new EJavaEntityImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	@Override
	public SortedSet<EJavaRepository> getRepositories() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
			.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
			.map(j -> new EJavaRepositoryImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public SortedSet<EJavaRest> getRests() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
			.map(j -> new EJavaRestImpl(this, j))
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
	public Optional<EUmlClassDiagram> getDomainClassDiagram() {
		try {
			EUmlClassDiagram classDiagram = EUmlClassDiagramImpl.of(FilenameUtils.concat(this.getPath(), "docs/diagrams/domain-class.md"));
			return Optional.of(classDiagram);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public void refresh() {
		this.javaUnits = null;
	}

	public static ESBootProject of(String path) {
		if (Files.exists(Paths.get(path))) {
			if (Stream.of(ProjectType.SPRINGBOOT1_PROJECT, ProjectType.SPRINGBOOT2_PROJECT).anyMatch(p -> p.equals(EDirectoryImpl.of(path).getProjectType()))) {
				return new ESBootProjectImpl(path);
			}
		}
		throw new IllegalArgumentException("O diretório não possui um projeto Spring Boot válido.");
	}

}
