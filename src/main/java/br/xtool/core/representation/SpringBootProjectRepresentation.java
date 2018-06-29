package br.xtool.core.representation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jdom2.JDOMException;

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

	private Set<JavaUnit> javaUnits = new HashSet<>();

	private SortedSet<EntityRepresentation> entities;

	private SortedSet<RepositoryRepresentation> repositories;

	private PomRepresentation pom;

	public SpringBootProjectRepresentation(String path, Set<JavaUnit> javaUnits) {
		super();
		this.path = path;
		this.javaUnits = javaUnits;
	}

	public PomRepresentation getPom() throws JDOMException, IOException {
		if (this.pom == null) {
			PomRepresentation.of(FilenameUtils.concat(this.path, "pom.xml")).ifPresent(pomRepresentation -> this.pom = pomRepresentation);
		}
		return pom;
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

	public String getMainDir() {
		return FilenameUtils.concat(this.path, "src/main/java");
	}

	public static boolean isValidProject(String path) {
		return Files.exists(Paths.get(path, "pom.xml"));
	}

}
