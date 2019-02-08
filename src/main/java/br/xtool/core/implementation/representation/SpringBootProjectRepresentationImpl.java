package br.xtool.core.implementation.representation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.springboot.ApplicationPropertiesRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.representation.springboot.JavaPackageRepresentation;
import br.xtool.core.representation.springboot.JavaSourceFolderRepresentation;
import br.xtool.core.representation.springboot.JpaProjectionRepresentation;
import br.xtool.core.representation.springboot.PomRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.util.RoasterUtil;
import lombok.SneakyThrows;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class SpringBootProjectRepresentationImpl extends ProjectRepresentationImpl implements SpringBootProjectRepresentation {

	private Collection<JavaUnit> javaUnits;

	private PomRepresentation pom;

	private ApplicationPropertiesRepresentation applicationProperties;

	private JavaClassRepresentation mainClass;

	public SpringBootProjectRepresentationImpl(Path path) {
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getBaseClassName()
	 */
	@Override
	public String getBaseClassName() {
		return this.getMainclass().getName().replaceAll("Application", "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getMainclass()
	 */
	@Override
	public JavaClassRepresentation getMainclass() {
		if (Objects.isNull(this.mainClass)) {
			// @formatter:off
			this.mainClass = this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("SpringBootApplication")))
				.map(javaUnit -> new JavaClassRepresentationImpl(this, javaUnit))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Não foi possível localizar a classe principal (@SpringBootApplication). Verifique se a mesma existe ou contêm erros."));
			// @formatter:on
		}
		return this.mainClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getRootPackage()
	 */
	@Override
	public JavaPackageRepresentation getRootPackage() {
		return this.getPom().getGroupId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getMainSourceFolder()
	 */
	@Override
	public JavaSourceFolderRepresentation getMainSourceFolder() {
		return new JavaSourceFolderRepresentationImpl(this, this.getPath().resolve("src/main/java"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getTestSourceFolder()
	 */
	@Override
	public JavaSourceFolderRepresentation getTestSourceFolder() {
		return new JavaSourceFolderRepresentationImpl(this, this.getPath().resolve("src/test/java"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.ESBootProject#getPom()
	 */
	@Override
	public PomRepresentation getPom() {
		if (Objects.isNull(this.pom)) {
			this.pom = PomRepresentationImpl.of(this, this.getPath().resolve("pom.xml"));
		}
		return this.pom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getApplicationProperties()
	 */
	@Override
	public ApplicationPropertiesRepresentation getApplicationProperties() {
		if (Objects.isNull(this.applicationProperties)) {
			this.applicationProperties = ApplicationPropertiesRepresentationImpl.of(this, this.getPath().resolve("src/main/resources/application.properties"));
		}
		return this.applicationProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getEnums()
	 */
	@Override
	public Collection<JavaEnumRepresentation> getEnums() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isEnum())
			.map(javaUnit -> javaUnit.<JavaEnumSource>getGoverningType())
			.map(j -> new JavaEnumRepresentationImpl(this, j))
			.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getEntities()
	 */
	@Override
	public SortedSet<EntityRepresentation> getEntities() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
			.map(j -> new EntityRepresentationImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getProjections()
	 */
	@Override
	public SortedSet<JpaProjectionRepresentation> getProjections() {
		// @formatter:off
		return this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
				.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
				.filter(j -> j.getName().endsWith("Projection"))
				.map(j -> new JpaProjectionRepresentationImpl(this,j))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getSpecifications()
	 */
	@Override
	public SortedSet<SpecificationRepresentation> getSpecifications() {
		// @formatter:off
		return this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getName().endsWith("Specification"))
				.map(j -> new SpecificationRepresentationImpl(this, j))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getServices()
	 */
	@Override
	public SortedSet<ServiceClassRepresentation> getServices() {
		// @formatter:off
		return this.getJavaUnits()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getName().endsWith("Service"))
				.map(j -> new ServiceClassRepresentationImpl(this, j))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getRepositories()
	 */
	@Override
	public SortedSet<RepositoryRepresentation> getRepositories() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
			.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
			.map(j -> new RepositoryRepresentationImpl(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getRests()
	 */
	@Override
	public SortedSet<RestClassRepresentation> getRests() {
		// @formatter:off
		return this.getJavaUnits()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
			.map(j -> new RestClassRepresentationImpl(this, j))
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
	 * 
	 * @see br.xtool.core.representation.EBootProject#getAssociatedAngularProject()
	 */
	@Override
	public Optional<NgProjectRepresentation> getAssociatedAngularProject() {
		String angularPath = this.getPath().toString().replace("-service", "");
		if (StringUtils.isNotEmpty(angularPath)) {
			if (Files.exists(Paths.get(angularPath))) {
				if (NgProjectRepresentation.isValid(Paths.get(angularPath))) {
					return Optional.of(new NgProjectRepresentationImpl(Paths.get(angularPath)));
				}
			}
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EBootProject#getDomainClassDiagram()
	 */
	@Override
	public PlantClassDiagramRepresentation getMainDomainClassDiagram() {
		return PlantClassDiagramRepresentationImpl.of(this.getPath().resolve("docs/diagrams/class/main.md"));
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.SpringBootProjectRepresentation#getClassDiagrams()
	 */
	@Override
	@SneakyThrows
	public List<PlantClassDiagramRepresentation> getClassDiagrams() {
		try (Stream<Path> paths = Files.walk(this.getPath().resolve("docs/diagrams/class"))) {
		    return paths
		        .filter(Files::isRegularFile)
		        .map(PlantClassDiagramRepresentationImpl::of)
		        .collect(Collectors.toList());
		} 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EProject#getFrameworkVersion()
	 */
	@Override
	public String getFrameworkVersion() {
		return this.getPom().getParentVersion().get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EProject#getProjectVersion()
	 */
	@Override
	public Version getProjectVersion() {
		Pattern v5pattern = Pattern.compile("1\\.5\\.\\d+\\.\\w*");
		Pattern v6pattern = Pattern.compile("2\\.\\d+\\.\\d+\\.\\w*");
		if (v5pattern.matcher(getFrameworkVersion()).matches())
			return Version.V1;
		if (v6pattern.matcher(getFrameworkVersion()).matches())
			return Version.V2;
		return Version.NONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EProject#refresh()
	 */
	@Override
	public void refresh() {
		this.javaUnits = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EProject#getProjectType()
	 */
	@Override
	public Type getProjectType() {
		return ProjectRepresentation.Type.SPRINGBOOT;
	}

}
