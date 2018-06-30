package br.xtool.support.core;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.enums.ProjectType;

@Component
public class SpringBoot1JpaSupport implements SpringBootSupport {

	@Override
	public ProjectType applyForType() {
		return ProjectType.SPRINGBOOT1_PROJECT;
	}

	@Override
	public void apply(SpringBootProjectRepresentation project) {
		
	}

}
