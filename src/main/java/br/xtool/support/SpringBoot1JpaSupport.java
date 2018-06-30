package br.xtool.support;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.FS;
import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.EPom.Dependency;
import br.xtool.core.representation.enums.ProjectType;
import br.xtool.support.core.SpringBootSupport;
import br.xtool.support.core.SupportType;

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
		fs.createEmptyPath("src/main/java/${rootPackage.dir}/domain", vars);
		fs.createEmptyPath("src/main/java/${rootPackage.dir}/repository", vars);
		project.getPom().addDependency(new Dependency("org.springframework.boot", "spring-boot-starter-data-jpa"));
		project.getPom().addDependency(new Dependency("com.h2database", "h2"));
		project.getPom().addDependency(new Dependency("com.oracle", "ojdbc6", "11.2.0.3.0"));
		project.getPom().addDependency(new Dependency("org.hibernate", "hibernate-java8"));
		project.getPom().addDependency(new Dependency("org.hibernate", "hibernate-ehcache"));
		project.getPom().commitUpdates();
	}

}
