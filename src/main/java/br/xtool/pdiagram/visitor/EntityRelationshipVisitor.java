package br.xtool.pdiagram.visitor;

import javax.validation.constraints.Size;

import br.xtool.pdiagram.RelationshipVisitor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.xtool.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.representation.springboot.JpaEntityAttributeRepresentation;

@Component
public class EntityRelationshipVisitor implements RelationshipVisitor {

	@Override
	public void visit(JpaEntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
		addJsonIgnorePropertiesAnnotation(attribute, plantRelationship);
		addSizeAnnotation(attribute, plantRelationship);
	}

	private void addSizeAnnotation(JpaEntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.getSourceMultiplicity().isToMany() && !plantRelationship.getSourceMultiplicity().isOptional()) {
			// @formatter:off
			attribute.getRoasterField().addAnnotation(Size.class)
				.setLiteralValue("min", String.valueOf(1));
			// @formatter:on
		}
	}

	private void addJsonIgnorePropertiesAnnotation(JpaEntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
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
