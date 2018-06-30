package br.xtool.core.event;

import br.xtool.core.representation.EDirectory;
import lombok.Getter;

@Getter
public class ChangeDirectoryEvent {

	private EDirectory directory;

	public ChangeDirectoryEvent(EDirectory directory) {
		super();
		this.directory = directory;
	}

}
