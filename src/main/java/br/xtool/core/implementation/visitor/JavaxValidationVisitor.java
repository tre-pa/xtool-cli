package br.xtool.core.implementation.visitor;

import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

@Component
public class JavaxValidationVisitor implements Visitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass)
	 */
	@Override
	public void visit(EntityRepresentation entity, PlantClassRepresentation umlClass) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
//		if (attr.isStringField()) {
//			val ann = attr.getRoasterField().addAnnotation(Size.class);
//			ann.setLiteralValue("max", String.valueOf(plantField.getMaxArrayLength().orElse(255)));
//			plantField.getMinArrayLength().ifPresent(v -> ann.setLiteralValue("min", String.valueOf(v)));
//		} else if (attr.isNumberField()) {
//			// @formatter:off
//			plantField.getMinArrayLength().ifPresent(minValue -> 
//				attr.addAnnotation(Min.class)
//					.getRoasterAnnotation()
//					.setLiteralValue(String.valueOf(minValue)));
//			plantField.getMaxArrayLength().ifPresent(maxValue -> 
//				attr.addAnnotation(Max.class)
//					.getRoasterAnnotation()
//					.setLiteralValue(String.valueOf(maxValue)));
//			// @formatter:on
//		}

	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToMany() && !umlRelationship.getSourceMultiplicity().isOptional()) {
			// @formatter:off
			attribute.getRoasterField().addAnnotation(Size.class)
				.setLiteralValue("min", String.valueOf(1));
			// @formatter:on
		}
	}

}
