package br.xtool.core.event;

import br.xtool.core.representation.DirectoryRepresentation;
import lombok.Getter;

@Getter
public class ChangeDirectoryEvent {

	private DirectoryRepresentation directory;

	public ChangeDirectoryEvent(DirectoryRepresentation directory) {
		super();
		this.directory = directory;
	}

}
