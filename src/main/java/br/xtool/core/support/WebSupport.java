package br.xtool.core.support;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EBootAppProperties;
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootProjectSupport;
import br.xtool.service.BootProjectService;
import strman.Strman;

@Component
public class WebSupport implements BootProjectSupport {

	@Autowired
	private BootProjectService bootProjectService;

	@Override
	public void apply(EBootProject project) {
		Path rootPackage = project.getMainSourceFolder().getPath().resolve(project.getRootPackage().getPath());
		this.bootProjectService.createDirectory(project, rootPackage.resolve("rest"));
	}

	@Override
	public void apply(EBootAppProperties appProperties) {
		// @formatter:off
		appProperties
			.set("server.context-path", "/%s", Strman.toKebabCase(appProperties.getProject().getName()))
			.set("server.port", "8080")
			.set("spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS", "false")
		.save();
	// @formatter:on
	}

	@Override
	public void apply(EBootPom pom) {
		// @formatter:off
		pom
		  .addDependency("org.springframework.boot", "spring-boot-starter-web")
		  .addDependency("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310")
		.save();
		// @formatter:on
	}

	@Override
	public boolean has(EBootProject project) {
		return project.getPom().hasArtifactId("spring-boot-starter-web");
	}

}
