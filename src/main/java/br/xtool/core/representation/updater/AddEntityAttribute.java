package br.xtool.core.representation.updater;

import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.updater.core.UpdateRequest;

public class AddEntityAttribute implements UpdateRequest<EEntity> {

	private String fieldName;

	@Override
	public boolean updatePolicy(EEntity representation) {
		return !representation.getSource().hasField(fieldName);
	}

	@Override
	public void apply(EEntity representation) {

	}

}
