package br.xtool.core.representation;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class SpringBootProjectRepresentation {

	@Getter
	private String path;

	@Getter
	private DirectoryRepresentation directory;

	private Set<JavaUnit> javaUnits = new HashSet<>();

	private SortedSet<EntityRepresentation> entities;

	private SortedSet<RepositoryRepresentation> repositories;

	private SortedSet<RestRepresentation> rests;

	private PomRepresentation pom;

	private ApplicationPropertiesRepresentation applicationProperties;

	public SpringBootProjectRepresentation(String path, Set<JavaUnit> javaUnits) {
		super();
		this.path = path;
		this.directory = new DirectoryRepresentation(path);
		this.javaUnits = javaUnits;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.directory.getBaseName();
	}

	public String getBaseClassName() {
		// @formatter:off
		return this.javaUnits
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("SpringBootApplication")))
				.map(j -> StringUtils.replace(j.getName(), "Application", ""))
				.findFirst()
				.orElse("");
		// @formatter:on
	}

	public ProjectType getProjectType() {
		return this.getDirectory().getProjectType();
	}

	public PackageRepresentation getRootPackage() {
		return this.getPom().getGroupId();
	}

	public PomRepresentation getPom() {
		if (this.pom == null) {
			PomRepresentation.of(FilenameUtils.concat(this.path, "pom.xml")).ifPresent(pomRepresentation -> this.pom = pomRepresentation);
		}
		return pom;
	}

	public ApplicationPropertiesRepresentation getApplicationProperties() {
		if (this.applicationProperties == null) {
			ApplicationPropertiesRepresentation.of(FilenameUtils.concat(this.path, "src/main/resources/application.properties"))
					.ifPresent(applicationProperties -> this.applicationProperties = applicationProperties);
		}
		return applicationProperties;
	}

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	public SortedSet<EntityRepresentation> getEntities() {
		if (this.entities == null) {
			// @formatter:off
			this.entities = this.javaUnits
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isClass())
					.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
					.map(j -> new EntityRepresentation(this, j))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return entities;
	}

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	public SortedSet<RepositoryRepresentation> getRepositories() {
		if (this.repositories == null) {
			// @formatter:off
			this.repositories = this.javaUnits
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
					.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
					.map(j -> new RepositoryRepresentation(this, j))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.repositories;
	}

	/**
	 * 
	 * @return
	 */
	public SortedSet<RestRepresentation> getRests() {
		if (this.rests == null) {
			// @formatter:off
			this.rests = this.javaUnits
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isClass())
					.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
					.map(j -> new RestRepresentation(this, j))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return rests;
	}

	public String getMainDir() {
		return FilenameUtils.concat(this.path, "src/main/java");
	}

	public static boolean isValidProject(String path) {
		return Files.exists(Paths.get(path, "pom.xml"));
	}

}
