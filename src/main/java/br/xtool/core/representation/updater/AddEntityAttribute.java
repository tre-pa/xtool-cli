package br.xtool.core.representation.updater;

import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.updater.core.UpdateRequest;

public class AddEntityAttribute implements UpdateRequest<EntityRepresentation> {

	private String fieldName;

	@Override
	public boolean updatePolicy(EntityRepresentation representation) {
		return !representation.getSource().hasField(fieldName);
	}

	@Override
	public void apply(EntityRepresentation representation) {

	}

}
