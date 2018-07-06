package br.xtool.support;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.FS;
import br.xtool.core.Log;
import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;
import br.xtool.support.core.SpringBootSupport;

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
	private FS fs;

	@Override
	public ProjectType getApplyForType() {
		return ProjectType.SPRINGBOOT1_PROJECT;
	}

	@Override
	public SupportType getType() {
		return SupportType.JPA;
	}

	@Override
	public void apply(ESpringBootProject project) {
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("projectName", project.getName())
				.put("projectVersion", project.getPom().getParentVersion())
				.put("rootPackage", project.getRootPackage())
				.put("baseClassName", project.getBaseClassName())
				.build();
		// @formatter:on
		Log.print(Log.cyan("\t-- Suporte JPA --"));
		fs.createEmptyPath("src/main/java/${rootPackage.dir}/domain", vars);
		fs.createEmptyPath("src/main/java/${rootPackage.dir}/repository", vars);
		addDependencies(project);
		addProperties(project);
	}

	private void addDependencies(ESpringBootProject project) {
		project.getPom().addDependency("org.springframework.boot", "spring-boot-starter-data-jpa");
		project.getPom().addDependency("com.h2database", "h2");
		project.getPom().addDependency("org.hibernate", "hibernate-java8");
		project.getPom().addDependency("com.oracle", "ojdbc6", "11.2.0.3.0");
		project.getPom().save();
	}

	private void addProperties(ESpringBootProject project) {
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
	public boolean hasSupport(ESpringBootProject project) {
		return project.getPom().hasArtifactId("spring-boot-starter-data-jpa");
	}

}
