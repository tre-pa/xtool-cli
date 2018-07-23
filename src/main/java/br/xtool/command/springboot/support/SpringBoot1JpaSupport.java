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
public class SpringBoot1JpaSupport implements SpringBootSupport {

	@Autowired
	private FileService fs;

	@Override
	public Type getApplyForType() {
		return Type.SPRINGBOOT_PROJECT;
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
		this.fs.createEmptyPath("src/main/java/${rootPackage.dir}/domain", vars);
		this.fs.createEmptyPath("src/main/java/${rootPackage.dir}/repository", vars);
		addDependencies(project);
		addProperties(project);
	}

	private void addDependencies(EBootProject project) {
		project.getPom().addDependency("org.springframework.boot", "spring-boot-starter-data-jpa");
		project.getPom().addDependency("com.h2database", "h2");
		project.getPom().addDependency("org.hibernate", "hibernate-java8");
		project.getPom().addDependency("com.oracle", "ojdbc6", "11.2.0.3.0");
		project.getPom().save();
	}

	private void addProperties(EBootProject project) {
		project.getApplicationProperties().set("spring.h2.console.enabled", "true");
		project.getApplicationProperties().set("spring.h2.console.path", "/h2");
		project.getApplicationProperties().set("spring.datasource.url", String.format("jdbc:h2:./target/db/%s;DB_CLOSE_ON_EXIT=FALSE", project.getName()));
		project.getApplicationProperties().set("spring.datasource.driver-class-name", "org.h2.Driver");
		project.getApplicationProperties().set("spring.datasource.username", "sa");
		project.getApplicationProperties().set("spring.datasource.password", "");
		project.getApplicationProperties().set("spring.datasource.sqlScriptEncoding", "UTF-8");
		project.getApplicationProperties().set("logging.level.org.hibernate.SQL", "DEBUG");
		project.getApplicationProperties().set("spring.jpa.properties.hibernate.format_sql", "true");
		project.getApplicationProperties().set("spring.jpa.hibernate.ddl-auto", "update");
		project.getApplicationProperties().set("spring.jpa.hibernate.use-new-id-generator-mappings", "true");
		project.getApplicationProperties().save();
	}

	@Override
	public boolean hasSupport(EBootProject project) {
		return project.getPom().hasArtifactId("spring-boot-starter-data-jpa");
	}

}
