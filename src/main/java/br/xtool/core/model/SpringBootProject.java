package br.xtool.core.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jdom2.JDOMException;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class SpringBootProject {

	private String path;

	private Set<JavaClassSource> javaClassSources = new HashSet<>();

	private Set<JavaInterfaceSource> javaInterfaceSources = new HashSet<>();

	private SortedSet<Entity> entities;

	private SortedSet<Repository> repositories;

	private Pom pom;

	private SpringBootProject() {
		super();
	}

	private SpringBootProject(String path) {
		super();
		this.path = path;
		this.buildJavaClassSources();
	}

	public static boolean isValidSpringBootProject(String path) {
		return Files.exists(Paths.get(path, "pom.xml"));
	}

	public static Optional<SpringBootProject> of(String path) {
		if (isValidSpringBootProject(path)) {
			SpringBootProject springBootProject = new SpringBootProject(path);
			return Optional.of(springBootProject);
		}
		return Optional.empty();
	}

	public Pom getPom() throws JDOMException, IOException {
		if(this.pom == null) {
			this.pom = new Pom(this.path);
		}
		return pom;
	}

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	public SortedSet<Entity> getEntities() {
		if (this.entities == null) {
			// @formatter:off
			this.entities = this.javaClassSources
					.parallelStream()
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
					.map(j -> new Entity(this, j))
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
	public SortedSet<Repository> getRepositories() {
		if (this.repositories == null) {
			// @formatter:off
			this.repositories = this.javaInterfaceSources
					.parallelStream()
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
					.map(Repository::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.repositories;
	}

	public Optional<Entity> getEntityFromRepository(Repository repository) {
		// @formatter:off
		return this.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(repository.getName()))
				.findFirst();
		// @formatter:on
	}
	
	private void buildJavaClassSources() {
		if (StringUtils.isNotEmpty(path)) {
			//// @formatter:off
			try {
				Files.walk(Paths.get(this.path))
					.filter(Files::isRegularFile)
					.filter(p -> p.toFile().getName().endsWith("java"))
					.map(p -> p.toFile())
					.forEach(this::parseAndAddJavaFile);
			} catch (IOException e) {
				e.printStackTrace();
			};
			// @formatter:on
		}
	}

	private void parseAndAddJavaFile(File javaFile) {
		try {
			JavaUnit javaUnit = Roaster.parseUnit(new FileInputStream(javaFile));
			if (javaUnit.getGoverningType().isClass()) {
				this.javaClassSources.add(javaUnit.getGoverningType());
			} else if (javaUnit.getGoverningType().isInterface()) {
				this.javaInterfaceSources.add(javaUnit.getGoverningType());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
