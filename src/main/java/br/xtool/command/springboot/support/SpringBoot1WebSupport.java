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
 * Adicionar suporte Web a um projeto Spring Boot existente.
 * 
 * @author jcruz
 *
 */
@Component
public class SpringBoot1WebSupport implements SpringBootSupport {

	@Autowired
	private FileService fs;

	@Override
	public Type getApplyForType() {
		return Type.SPRINGBOOT;
	}

	@Override
	public SupportType getType() {
		return SupportType.WEB;
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
		ConsoleLog.print(ConsoleLog.cyan("\t-- Suporte WEB --"));
		//		this.fs.createEmptyPath("src/main/java/${rootPackage.dir}/rest", vars);
		addDependencies(project);
		addProperties(project);
	}

	private void addDependencies(EBootProject project) {
		project.getPom().addDependency("org.springframework.boot", "spring-boot-starter-web");
		project.getPom().addDependency("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310");
		project.getPom().save();
	}

	private void addProperties(EBootProject project) {
		project.getApplicationProperties().set("server.context-path", String.format("/%s", project.getName()));
		project.getApplicationProperties().set("server.port", "8080");
		project.getApplicationProperties().set("spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS", "false");
		project.getApplicationProperties().set("spring.jackson.serialization.FAIL_ON_EMPTY_BEANS", "false");
		project.getApplicationProperties().save();
	}

	@Override
	public boolean hasSupport(EBootProject project) {
		return project.getPom().hasArtifactId("spring-boot-starter-web") && project.getApplicationProperties().hasProperty("server.context-path");
	}

}
