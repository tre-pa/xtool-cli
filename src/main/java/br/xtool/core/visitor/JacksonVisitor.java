package br.xtool.core.visitor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

@Component
public class JacksonVisitor implements Visitor {

	@Override
	public void visit(EntityRepresentation entity, PlantClassRepresentation umlClass) {

	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship) {
		// javaField.getRelationship().get().getTargetClass().getJavaFields();
		// @formatter:off
		String[] relationships = plantRelationship.getTargetClass().getRelationships().stream()
				.map(relationship -> relationship.getSourceRole())
				.toArray(String[]::new);
		// @formatter:on
		if (relationships.length > 0) {
			// @formatter:off
			attribute.addAnnotation(JsonIgnoreProperties.class).getRoasterAnnotation()
				.setLiteralValue("allowSetters", "true")
				.setStringArrayValue("value", relationships);
			// @formatter:on
		}
	}

}
