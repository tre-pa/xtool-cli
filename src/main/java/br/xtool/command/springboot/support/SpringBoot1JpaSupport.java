package br.xtool.command.springboot.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.command.springboot.support.core.SpringBootSupport;
import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EProject.Type;
import br.xtool.core.service.FileService;

/**
 * 
 * Adicionar suporte JPA a um projeto Spring Boot existente.
 * 
 * @author jcruz
 *
 */
@Component
@Deprecated
public class SpringBoot1JpaSupport implements SpringBootSupport {

	@Autowired
	private FileService fs;

	@Override
	public Type getApplyForType() {
		return Type.SPRINGBOOT;
	}

	@Override
	public SupportType getType() {
		return SupportType.JPA;
	}

	@Override
	public void apply(EBootProject project) {
		//// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("projectName", project.getName());
		vars.put("projectVersion", project.getPom().getParentVersion());
		vars.put("rootPackage", project.getRootPackage());
		vars.put("baseClassName", project.getBaseClassName());
		// @formatter:on
		ConsoleLog.print(ConsoleLog.cyan("\t-- Suporte JPA --"));
		//		this.fs.createEmptyPath("src/main/java/${rootPackage.dir}/domain", vars);
		//		this.fs.createEmptyPath("src/main/java/${rootPackage.dir}/repository", vars);
		addDependencies(project);
		addProperties(project);
	}

	private void addDependencies(EBootProject project) {
		// @formatter:off
		project.getPom()
			.addDependency("org.springframework.boot", "spring-boot-starter-data-jpa")
			.addDependency("com.h2database", "h2")
			.addDependency("org.hibernate", "hibernate-java8")
			.addDependency("com.oracle", "ojdbc6", "11.2.0.3.0")
		.save();
		// @formatter:on
	}

	private void addProperties(EBootProject project) {
		// @formatter:off
		project.getApplicationProperties()
			.set("spring.h2.console.enabled", "true")
			.set("spring.h2.console.path", "/h2")
			.set("spring.datasource.url", String.format("jdbc:h2:./target/db/%s;DB_CLOSE_ON_EXIT=FALSE", project.getName()))
			.set("spring.datasource.driver-class-name", "org.h2.Driver")
			.set("spring.datasource.username", "sa")
			.set("spring.datasource.password", "")
			.set("spring.datasource.sqlScriptEncoding", "UTF-8")
			.set("logging.level.org.hibernate.SQL", "DEBUG")
			.set("spring.jpa.properties.hibernate.format_sql", "true")
			.set("spring.jpa.hibernate.ddl-auto", "update")
			.set("spring.jpa.hibernate.use-new-id-generator-mappings", "true")
		.save();
		// @formatter:on
	}

	@Override
	public boolean hasSupport(EBootProject project) {
		return project.getPom().hasArtifactId("spring-boot-starter-data-jpa");
	}

}
