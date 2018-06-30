package br.xtool.support.core;

import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;

public interface SpringBootSupport {

	public ProjectType getApplyForType();

	public SupportType getType();

	public void apply(ESpringBootProject project);
}
