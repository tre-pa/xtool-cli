package br.xtool.mapper.jackson;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.AssociationRepresentation;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.mapper.core.JacksonMapper;

@Component
public class AddJsonIgnorePropertiesToBidirectionalAssociations implements JacksonMapper {

	@Override
	public void apply(EntityRepresentation t) {
		t.getAssociations().stream().filter(ass -> ass.isBidirectional()).forEach(ass -> this.addJsonIgnorePropertiesToBidirectionalAssociation(t, ass));
	}

	private void addJsonIgnorePropertiesToBidirectionalAssociation(EntityRepresentation entity, AssociationRepresentation association) {
		if (!association.getAttributeSource().hasAnnotation("JsonIgnoreProperties")) {
			association.getEntitySource().addAnnotation(ann -> {
				entity.addImport("com.fasterxml.jackson.annotation.JsonIgnoreProperties");
				ann.setStringArrayValue("value", new String[] { association.getAttributeTarget().get().getName() });
			});
		}
		if (!association.getEntityTarget().hasAnnotation("JsonIgnoreProperties")) {
			association.getEntityTarget().addAnnotation(ann -> {
				association.getEntityTarget().addImport("com.fasterxml.jackson.annotation.JsonIgnoreProperties");
				ann.setStringArrayValue("value", new String[] { association.getAttributeSource().getName() });
			});
		}
	}
}
