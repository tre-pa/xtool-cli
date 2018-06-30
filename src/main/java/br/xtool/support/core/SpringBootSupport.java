package br.xtool.support.core;

import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.enums.ProjectType;

public interface SpringBootSupport {

	public ProjectType applyForType();

	public void apply(SpringBootProjectRepresentation project);
}
