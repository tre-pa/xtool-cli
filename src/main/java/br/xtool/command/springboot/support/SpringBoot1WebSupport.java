package br.xtool.command.springboot.support;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.FS;
import br.xtool.command.springboot.support.core.SpringBootSupport;
import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;

/**
 * 
 * Adicionar suporte Web a um projeto Spring Boot existente.
 * 
 * @author jcruz
 *
 */
@Component
public class SpringBoot1WebSupport implements SpringBootSupport {

	@Autowired
	private FS fs;

	@Override
	public ProjectType getApplyForType() {
		return ProjectType.SPRINGBOOT1_PROJECT;
	}

	@Override
	public SupportType getType() {
		return SupportType.WEB;
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
		ConsoleLog.print(ConsoleLog.cyan("\t-- Suporte WEB --"));
		fs.createEmptyPath("src/main/java/${rootPackage.dir}/rest", vars);
		addDependencies(project);
		addProperties(project);
	}

	private void addDependencies(ESpringBootProject project) {
		project.getPom().addDependency("org.springframework.boot", "spring-boot-starter-web");
		project.getPom().addDependency("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310");
		project.getPom().save();
	}

	private void addProperties(ESpringBootProject project) {
		project.getApplicationProperties().set("server.context-path", project.getName());
		project.getApplicationProperties().set("server.port", "8080");
		project.getApplicationProperties().set("spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS", "false");
		project.getApplicationProperties().set("spring.jackson.serialization.FAIL_ON_EMPTY_BEANS", "false");
		project.getApplicationProperties().save();
	}

	@Override
	public boolean hasSupport(ESpringBootProject project) {
		return project.getPom().hasArtifactId("spring-boot-starter-web") && project.getApplicationProperties().hasProperty("server.context-path");
	}

}
