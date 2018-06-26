package br.xtool.core.representation;

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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
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

	private Set<JavaClassSource> javaClassSources = new HashSet<>();

	private Set<JavaInterfaceSource> javaInterfaceSources = new HashSet<>();

	private SortedSet<EntityRepresentation> entities;

	private SortedSet<RepositoryRepresentation> repositories;

	private PomRepresentation pom;

	private SpringBootProjectRepresentation() {
		super();
	}

	private SpringBootProjectRepresentation(String path) {
		super();
		this.path = path;
		this.buildJavaClassSources();
	}

	public static boolean isValidSpringBootProject(String path) {
		return Files.exists(Paths.get(path, "pom.xml"));
	}

	public static Optional<SpringBootProjectRepresentation> of(String path) {
		if (isValidSpringBootProject(path)) {
			SpringBootProjectRepresentation springBootProject = new SpringBootProjectRepresentation(path);
			return Optional.of(springBootProject);
		}
		return Optional.empty();
	}

	public PomRepresentation getPom() throws JDOMException, IOException {
		if (this.pom == null) {
			this.pom = new PomRepresentation(FilenameUtils.concat(this.path, "pom.xml"));
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
			this.entities = this.javaClassSources
					.parallelStream()
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
			this.repositories = this.javaInterfaceSources
					.parallelStream()
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
