package br.xtool.core.representation.updater;

import br.xtool.core.representation.PomRepresentation;
import br.xtool.core.representation.updater.core.UpdateRequest;

public class AddPomDependency implements UpdateRequest<PomRepresentation> {

	@Override
	public boolean updatePolicy(PomRepresentation representation) {
		return false;
	}

	@Override
	public void apply(PomRepresentation representation) {

	}

}
