package br.xtool.mapper.jackson;

import org.springframework.stereotype.Component;

import br.xtool.core.model.Association;
import br.xtool.core.model.Entity;
import br.xtool.mapper.core.JacksonMapper;

@Component
public class AddJsonIgnorePropertiesToBidirectionalAssociations implements JacksonMapper {

	@Override
	public void apply(Entity t) {
		t.getAssociations().stream().filter(ass -> ass.isBidirectional()).forEach(ass -> this.addJsonIgnorePropertiesToBidirectionalAssociation(t, ass));
	}

	private void addJsonIgnorePropertiesToBidirectionalAssociation(Entity entity, Association association) {
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
