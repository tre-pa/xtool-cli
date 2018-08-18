package br.xtool.core.support;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EBootAppProperties;
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootProjectSupport;
import strman.Strman;

@Component
public class WebSupport implements BootProjectSupport {

	@Override
	public void apply(EBootProject project) {
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
