package br.xtool.core.representation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.enums.ProjectType;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe que representa um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@Slf4j
public class ESpringBootProject extends EProject {

	private Map<String, JavaUnit> javaUnits;

	private EPom pom;

	private EApplicationProperties applicationProperties;

	private Optional<EClass> mainClass;

	public ESpringBootProject(String path) {
		super(path);
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
			this.mainClass = this.getJavaUnits().values()
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
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Entity")))
			.map(j -> new EEntity(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	public SortedSet<ERepository> getRepositories() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isInterface())
			.map(javaUnit -> javaUnit.<JavaInterfaceSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("Repository")))
			.map(j -> new ERepository(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * 
	 * @return
	 */
	public SortedSet<ERest> getRests() {
		// @formatter:off
		return this.getJavaUnits().values()
			.parallelStream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.filter(j -> j.getAnnotations().stream().anyMatch(ann -> ann.getName().equals("RestController")))
			.map(j -> new ERest(this, j))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	public Map<String, JavaUnit> getJavaUnits() {
		if (Objects.isNull(this.javaUnits)) {
			// @formatter:off
			this.javaUnits = this.getDirectory().getAllFiles().stream()
				.filter(file -> file.getName().endsWith(".java"))
				.map(this::createJavaUnit)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(javaUnit -> javaUnit.getGoverningType().getName(), Function.identity()));
			// @formatter:on
		}
		return this.javaUnits;
	}

	private Optional<JavaUnit> createJavaUnit(File javaFile) {
		try {
			JavaUnit javaUnit = Roaster.parseUnit(new FileInputStream(javaFile));
			return Optional.of(javaUnit);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public String getMainDir() {
		return FilenameUtils.concat(this.getPath(), "src/main/java");
	}

	public static Optional<ESpringBootProject> of(String path) {
		if (Stream.of(ProjectType.SPRINGBOOT1_PROJECT, ProjectType.SPRINGBOOT2_PROJECT).anyMatch(p -> p.equals(EDirectory.of(path).getProjectType()))) {
			ESpringBootProject project = new ESpringBootProject(path);
			return Optional.of(project);
		}
		return Optional.empty();
	}

	@Override
	public void onFileCreate(File file) {
		log.info("onFileCreate: {}", file.getName());
		this.createJavaUnit(file).ifPresent(javaUnit -> this.javaUnits.put(file.getAbsolutePath(), javaUnit));
	}

	@Override
	public void onFileChange(File file) {
		log.info("onFileChange: {}", file.getName());
		this.createJavaUnit(file).ifPresent(javaUnit -> this.javaUnits.put(file.getAbsolutePath(), javaUnit));
	}

	@Override
	public void onFileDelete(File file) {
		log.info("onFileDelete: {}", file.getName());
		this.javaUnits.remove(file.getAbsolutePath());
	}

}
