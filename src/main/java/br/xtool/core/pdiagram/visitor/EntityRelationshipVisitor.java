package br.xtool.core.pdiagram.visitor;

import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.xtool.core.pdiagram.RelationshipVisitor;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;

@Component
public class EntityRelationshipVisitor implements RelationshipVisitor {

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
		addJsonIgnorePropertiesAnnotation(attribute, plantRelationship);
		addSizeAnnotation(attribute, plantRelationship);
	}

	private void addSizeAnnotation(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.getSourceMultiplicity().isToMany() && !plantRelationship.getSourceMultiplicity().isOptional()) {
			// @formatter:off
			attribute.getRoasterField().addAnnotation(Size.class)
				.setLiteralValue("min", String.valueOf(1));
			// @formatter:on
		}
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
