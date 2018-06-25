package br.xtool.mapper.jpa;

import org.springframework.stereotype.Component;

import br.xtool.core.model.Attribute;
import br.xtool.core.model.Entity;
import br.xtool.mapper.core.JpaMapper;

@Component
public class InitializeCollectionAttributes implements JpaMapper {

	@Override
	public void apply(Entity t) {
		// @formatter:off
		t.getAttributes().stream()
			.filter(attr -> attr.isCollection())
			.forEach(attr -> this.initalizeCollectionAttribute(t, attr));
		// @formatter:on
	}

	private void initalizeCollectionAttribute(Entity entity, Attribute attribute) {
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
