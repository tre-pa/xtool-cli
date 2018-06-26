package br.xtool.mapper.jpa;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.AttributeRepresentation;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.mapper.core.JpaMapper;

@Component
public class InitializeCollectionAttributes implements JpaMapper {

	@Override
	public void apply(EntityRepresentation t) {
		// @formatter:off
		t.getAttributes().stream()
			.filter(attr -> attr.isCollection())
			.forEach(attr -> this.initalizeCollectionAttribute(t, attr));
		// @formatter:on
	}

	private void initalizeCollectionAttribute(EntityRepresentation entity, AttributeRepresentation attribute) {
		switch (attribute.getType().getName()) {
		case "List":
			entity.addImport("java.util.ArrayList");
			attribute.setLiteralInitialize("new ArrayList<>()");
			break;
		case "Set":
			break;
		default:
			break;
		}
	}

}
