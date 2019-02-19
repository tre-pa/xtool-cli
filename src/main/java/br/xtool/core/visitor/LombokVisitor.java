package br.xtool.core.visitor;

import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
public class LombokVisitor implements Visitor {

	@Override
	public void visit(EntityRepresentation entity, PlantClassRepresentation umlClass) {
		entity.addAnnotation(Getter.class);
		entity.addAnnotation(Setter.class);
		entity.addAnnotation(NoArgsConstructor.class);
		// @formatter:off
		umlClass.getTaggedValueAsArray("ToString.of").ifPresent(tagValues -> entity
				.addAnnotation(ToString.class)
				.getRoasterAnnotation()
				.setStringArrayValue("of", tagValues));
		umlClass.getTaggedValueAsArray("EqualsAndHashCode.of").ifPresent(tagValues -> entity
				.addAnnotation(EqualsAndHashCode.class)
				.getRoasterAnnotation()
				.setStringArrayValue("of", tagValues));
		// @formatter:on
	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation umlRelationship) {

	}

}
