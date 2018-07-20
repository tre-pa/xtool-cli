package br.xtool.core.event;

import br.xtool.core.representation.EDirectory;
import lombok.Getter;

@Getter
public class ChangeWorkingProjectEvent {

	private EDirectory directory;

	public ChangeWorkingProjectEvent(EDirectory directory) {
		super();
		this.directory = directory;
	}

}
