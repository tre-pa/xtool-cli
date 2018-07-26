package br.xtool.core.representation.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EBootAppProperties;
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaEntity;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EBootRepository;
import br.xtool.core.representation.EBootRest;
import br.xtool.core.representation.EJavaSourceFolder;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EUmlClassDiagram;
import br.xtool.core.util.RoasterUtil;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class EBootProjectImpl extends EProjectImpl implements EBootProject {

	private Map<String, JavaUnit> javaUnits;

	private EBootPom pom;

	private EBootAppProperties applicationProperties;

	private EJavaClass mainClass;

	public EBootProjectImpl(Path path) {
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootProject#getMainclass()
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootProject#getRootPackage()
	 */
	@Override
	public EJavaPackage getRootPackage() {
		return this.getPom().getGroupId();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootProject#getMainSourceFolder()
	 */
	@Override
	public EJavaSourceFolder getMainSourceFolder() {
		return new EJavaSourceFolderImpl(this.getPath().resolve("src/main/java"));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootProject#getTestSourceFolder()
	 */
	@Override
	public EJavaSourceFolder getTestSourceFolder() {
		return new EJavaSourceFolderImpl(this.getPath().resolve("src/test/java"));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootProject#getPom()
	 */
	@Override
	public EBootPom getPom() {
		if (Objects.isNull(this.pom)) {
			this.pom = EBootPomImpl.of(this.getPath().resolve("pom.xml"));
		}
		return this.pom;
	}

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	@Override
	public EBootAppProperties getApplicationProperties() {
		if (Objects.isNull(this.applicationProperties)) {
			this.applicationProperties = EBootAppPropertiesImpl.of(this.getPath().resolve("src/main/resources/application.properties"));
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
	public SortedSet<EBootRepository> getRepositories() {
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
	public SortedSet<EBootRest> getRests() {
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
			this.javaUnits = this.listAllFiles().stream()
				.filter(path -> path.toString().endsWith(".java"))
				.map(Path::toFile)
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
		throw new UnsupportedOperationException();
		//		String angularPath = EDirectoryImpl.of(this.getDirectory().getPath().replace("-service", ""));
		//		return ENgProjectImpl.of(angularPath);
	}

	@Override
	public Optional<EUmlClassDiagram> getDomainClassDiagram() {
		try {
			EUmlClassDiagram classDiagram = EUmlClassDiagramImpl.of(this.getPath().resolve("docs/diagrams/domain-class.md"));
			return Optional.of(classDiagram);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public String getFrameworkVersion() {
		return this.getPom().getParentVersion().get();
	}

	@Override
	public void refresh() {
		this.javaUnits = null;
	}

	@Override
	public Type getProjectType() {
		return EProject.Type.SPRINGBOOT;
	}

}
