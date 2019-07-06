package br.xtool.core.template.angular;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.template.FSTemplate;
import lombok.AllArgsConstructor;
import strman.Strman;

@AllArgsConstructor
public class NgEnumFSTemplate extends FSTemplate {

	private JavaEnumRepresentation javaEnum;

	private NgProjectRepresentation project;

	@Override
	protected void configure() {
		put("Strman", Strman.class);
		put("project", project);
		put("javaEnumFileName", NgClassRepresentation.genFileName(javaEnum.getName()));
		put("javaEnumClassName", javaEnum.getName());
		put("javaEnum", javaEnum);
		put("javaEnumConstants", StringUtils.join(javaEnum.getConstants(), ","));

		source("angular", project.getProjectVersion().getName(), "enums");
		destination(project.getNgAppModule().getPath().getParent().toString(), "domain/enums");
	}

}
