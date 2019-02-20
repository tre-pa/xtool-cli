package br.xtool.core.implementation.visitor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.visitor.RelationshipVisitor;

@Component
public class EntityRelationshipVisitor implements RelationshipVisitor {

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
		addJsonIgnorePropertiesAnnotation(attribute, plantRelationship);
	}

	private void addJsonIgnorePropertiesAnnotation(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
		// @formatter:off
		String[] relationships = plantRelationship.getTargetClass().getRelationships().stream()
				.map(relationship -> relationship.getSourceRole())
				.toArray(String[]::new);
		if (relationships.length > 0) {
			attribute.addAnnotation(JsonIgnoreProperties.class).getRoasterAnnotation()
				.setLiteralValue("allowSetters", "true")
				.setStringArrayValue("value", relationships);
			// @formatter:on
		}
	}

}
