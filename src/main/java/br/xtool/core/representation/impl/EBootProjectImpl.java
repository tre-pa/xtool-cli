package br.xtool.core.representation.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EBootAppProperties;
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootRepository;
import br.xtool.core.representation.EBootRest;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJavaSourceFolder;
import br.xtool.core.representation.EJpaEntity;
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

	private Collection<JavaUnit> javaUnits;

	private EBootPom pom;

	private EBootAppProperties applicationProperties;

	private EJavaClass mainClass;

	public EBootProjectImpl(Path path) {
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EBootProject#getBaseClassName()
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
			this.mainClass = this.getJavaUnits()
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
		return new EJavaSourceFolderImpl(this, this.getPath().resolve("src/main/java"));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootProject#getTestSourceFolder()
	 */
	@Override
	public EJavaSourceFolder getTestSourceFolder() {
		return new EJavaSourceFolderImpl(this, this.getPath().resolve("src/test/java"));
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EBootProject#getApplicationProperties()
	 */
	@Override
	public EBootAppProperties getApplicationProperties() {
		if (Objects.isNull(this.applicationProperties)) {
			this.applicationProperties = EBootAppPropertiesImpl.of(this.getPath().resolve("src/main/resources/application.properties"));
		}
		return this.applicationProperties;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EBootProject#getEntities()
	 */
	@Override
	public SortedSet<EJpaEntity> getEntities() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
			.map(j -> new EJpaEntityImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EBootProject#getRepositories()
	 */
	@Override
	public SortedSet<EBootRepository> getRepositories() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
			.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
			.map(j -> new EBootRepositoryImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EBootProject#getRests()
	 */
	@Override
	public SortedSet<EBootRest> getRests() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
			.map(j -> new EJavaRestImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	public Collection<JavaUnit> getJavaUnits() {
		if (Objects.isNull(this.javaUnits)) {
			// @formatter:off
			this.javaUnits = this.listAllFiles().stream()
				.filter(path -> path.toString().endsWith(".java"))
				.map(Path::toFile)
				.map(RoasterUtil::loadJavaUnit)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
			// @formatter:on
		}
		return this.javaUnits;
	}

	@Override
	public Collection<JavaUnit> getRoasterJavaUnits() {
		return this.getJavaUnits();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EBootProject#getAssociatedAngularProject()
	 */
	@Override
	public Optional<ENgProject> getAssociatedAngularProject() {
		String angularPath = this.getPath().toString().replace("-service", "");
		if (StringUtils.isNotEmpty(angularPath)) {
			if (Files.exists(Paths.get(angularPath))) {
				if (ENgProject.isValid(Paths.get(angularPath))) {
					return Optional.of(new ENgProjectImpl(Paths.get(angularPath)));
				}
			}
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EBootProject#getDomainClassDiagram()
	 */
	@Override
	public EUmlClassDiagram getDomainClassDiagram() {
		return EUmlClassDiagramImpl.of(this.getPath().resolve("docs/diagrams/domain-class.md"));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EProject#getFrameworkVersion()
	 */
	@Override
	public String getFrameworkVersion() {
		return this.getPom().getParentVersion().get();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EProject#getProjectVersion()
	 */
	@Override
	public Version getProjectVersion() {
		Pattern v5pattern = Pattern.compile("1\\.5\\.\\d+\\.\\w*");
		Pattern v6pattern = Pattern.compile("2\\.\\d+\\.\\d+\\.\\w*");
		if (v5pattern.matcher(getFrameworkVersion()).matches()) return Version.V1;
		if (v6pattern.matcher(getFrameworkVersion()).matches()) return Version.V2;
		return Version.NONE;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EProject#refresh()
	 */
	@Override
	public void refresh() {
		this.javaUnits = null;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EProject#getProjectType()
	 */
	@Override
	public Type getProjectType() {
		return EProject.Type.SPRINGBOOT;
	}

}
