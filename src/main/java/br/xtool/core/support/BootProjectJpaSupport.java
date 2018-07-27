package br.xtool.core.support;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootSupport;
import br.xtool.core.service.FileService;

@Component
public class BootProjectJpaSupport implements BootSupport {

	@Autowired
	private FileService fs;

	@Override
	public void apply(EBootProject project) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("projectName", project.getName());
				put("projectVersion", project.getPom().getParentVersion());
				put("rootPackage", project.getRootPackage());
				put("baseClassName", project.getBaseClassName());
			}
		};
		Path templatesPath = Paths.get("springboot").resolve(project.getProjectVersion().getName()).resolve("support/jpa");
		this.fs.copy(templatesPath, vars, project);
		// @formatter:off
		project.getPom()
			.addDependency("org.springframework.boot", "spring-boot-starter-data-jpa")
			.addDependency("com.h2database", "h2")
			.addDependency("org.hibernate", "hibernate-java8")
			.addDependency("com.oracle", "ojdbc6", "11.2.0.3.0")
		.save();
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
	public boolean has(EBootProject project) {
		return project.getPom().hasArtifactId("spring-boot-starter-data-jpa");
	}

}
