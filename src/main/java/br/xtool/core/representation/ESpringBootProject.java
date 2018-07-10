package br.xtool.core.representation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public class ESpringBootProject extends EProject {

	private Map<String, JavaUnit> javaUnits = new HashMap<>();

	private SortedSet<EEntity> entities;

	private SortedSet<ERepository> repositories;

	private SortedSet<ERest> rests;

	private EPom pom;

	private EApplicationProperties applicationProperties;

	private Optional<EClass> mainClass;

	public ESpringBootProject(String path, Map<String, JavaUnit> javaUnits) {
		super(path);
		this.javaUnits = javaUnits;
	}

	/**
	 * Retorna o nome da classe base. O nome da classe base é o nome da classe que
	 * possui a annotation @SpringBootApplication sem o sufixo 'Application'
	 * 
	 * @return Nome da classe base.
	 */
	public String getBaseClassName() {
		return this.getMainclass().get().getName().replaceAll("Application", "");
	}

	/**
	 * 
	 * @return
	 */
	public Optional<EClass> getMainclass() {
		if (Objects.isNull(this.mainClass)) {
			// @formatter:off
			this.mainClass = this.javaUnits.values()
					.parallelStream()
					.filter(javaUnit -> javaUnit.getGoverningType().isClass())
					.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
					.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("SpringBootApplication")))
					.map(javaUnit -> new EClass(this, javaUnit))
					.findFirst();
			// @formatter:on
		}
		return this.mainClass;
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
		if (Objects.isNull(this.pom)) {
			this.pom = EPom.of(FilenameUtils.concat(this.getPath(), "pom.xml")).orElse(null);
		}
		return this.pom;
	}

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	public EApplicationProperties getApplicationProperties() {
		if (Objects.isNull(this.applicationProperties)) {
			this.applicationProperties = EApplicationProperties.of(FilenameUtils.concat(this.getPath(), "src/main/resources/application.properties")).orElse(null);
		}
		return this.applicationProperties;
	}

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	public SortedSet<EEntity> getEntities() {
		if (Objects.isNull(this.entities)) {
			// @formatter:off
			this.entities = this.javaUnits.values()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
				.map(j -> new EEntity(this, j))
				.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.entities;
	}

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	public SortedSet<ERepository> getRepositories() {
		if (Objects.isNull(this.repositories)) {
			// @formatter:off
			this.repositories = this.javaUnits.values()
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
		if (Objects.isNull(this.rests)) {
			// @formatter:off
			this.rests = this.javaUnits.values()
				.parallelStream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
				.map(j -> new ERest(this, j))
				.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.rests;
	}

	public String getMainDir() {
		return FilenameUtils.concat(this.getPath(), "src/main/java");
	}

}
