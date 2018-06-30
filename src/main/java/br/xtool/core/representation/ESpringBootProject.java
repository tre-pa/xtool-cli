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

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class ESpringBootProject extends EProject {

	private Set<JavaUnit> javaUnits = new HashSet<>();

	private SortedSet<EEntity> entities;

	private SortedSet<ERepository> repositories;

	private SortedSet<ERest> rests;

	private EPom pom;

	private EApplicationProperties applicationProperties;

	public ESpringBootProject(String path, Set<JavaUnit> javaUnits) {
		super(path);
		this.javaUnits = javaUnits;
	}

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	public String getName() {
		return this.getDirectory().getBaseName();
	}

	/**
	 * Retorna o nome da classe base. O nome da classe base é o nome da classe que
	 * possui a annotation @SpringBootApplication sem o sufixo 'Application'
	 * 
	 * @return Nome da classe base.
	 */
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

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	public ProjectType getProjectType() {
		return this.getDirectory().getProjectType();
	}

	/**
	 * Retorna o pacote raiz.
	 * 
	 * @return
	 */
	public EPackage getRootPackage() {
		return this.getPom().getGroupId();
	}

	/**
	 * Retorna a representação do arquivo pom.xml
	 * 
	 * @return
	 */
	public EPom getPom() {
		if (this.pom == null) {
			EPom.of(FilenameUtils.concat(this.getPath(), "pom.xml")).ifPresent(pomRepresentation -> this.pom = pomRepresentation);
		}
		return pom;
	}

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	public EApplicationProperties getApplicationProperties() {
		if (this.applicationProperties == null) {
			EApplicationProperties.of(FilenameUtils.concat(this.getPath(), "src/main/resources/application.properties"))
					.ifPresent(applicationProperties -> this.applicationProperties = applicationProperties);
		}
		return applicationProperties;
	}

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	public SortedSet<EEntity> getEntities() {
		if (this.entities == null) {
			// @formatter:off
			this.entities = this.javaUnits
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isClass())
					.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
					.map(j -> new EEntity(this, j))
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
	public SortedSet<ERepository> getRepositories() {
		if (this.repositories == null) {
			// @formatter:off
			this.repositories = this.javaUnits
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
					.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
					.map(j -> new ERepository(this, j))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.repositories;
	}

	/**
	 * 
	 * @return
	 */
	public SortedSet<ERest> getRests() {
		if (this.rests == null) {
			// @formatter:off
			this.rests = this.javaUnits
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isClass())
					.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
					.map(j -> new ERest(this, j))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return rests;
	}

	public String getMainDir() {
		return FilenameUtils.concat(this.getPath(), "src/main/java");
	}

	public static boolean isValidProject(String path) {
		return Files.exists(Paths.get(path, "pom.xml"));
	}

}
