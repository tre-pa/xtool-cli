package br.xtool.core.representation.updater;

import br.xtool.core.representation.EPom;
import br.xtool.core.representation.updater.core.UpdateRequest;

public class AddPomDependency implements UpdateRequest<EPom> {

	@Override
	public boolean updatePolicy(EPom representation) {
		return false;
	}

	@Override
	public void apply(EPom representation) {

	}

}
