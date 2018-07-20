package br.xtool.core.event;

import br.xtool.core.representation.EProject;
import lombok.Getter;

@Getter
public class ChangeWorkingProjectEvent {

	private EProject project;

	public ChangeWorkingProjectEvent(EProject project) {
		super();
		this.project = project;
	}

}
