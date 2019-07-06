package br.xtool.core.template.angular;

import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.template.FSTemplate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NgServiceFSTemplate extends FSTemplate {

	private EntityRepresentation entity;

	private NgProjectRepresentation project;

	@Override
	protected void configure() {
		put("entityFileName", NgClassRepresentation.genFileName(entity.getName()));
		put("entity", entity);

		source("angular", project.getProjectVersion().getName(), "service");
		destination(project.getNgAppModule().getPath().getParent().toString(), "service");
	}
}
