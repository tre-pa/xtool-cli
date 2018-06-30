package br.xtool.support;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.FS;
import br.xtool.core.representation.SpringBootProjectRepresentation;
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
	public void apply(SpringBootProjectRepresentation project) {
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
	}

}
