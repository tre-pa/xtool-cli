package br.xtool.core.visitor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipComposition;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldNotNullType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldTransientType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldUniqueType;
import lombok.experimental.var;

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
		if (attr.isStringField()) {
			var ann = attr.getRoasterField().addAnnotation(Size.class);
			plantField.getMinArrayLength().ifPresent(v -> ann.setLiteralValue("min", String.valueOf(v)));
			ann.setLiteralValue("max", String.valueOf(plantField.getMaxArrayLength().orElse(255)));
		} else if (attr.isNumberField()) {
			plantField.getMinArrayLength().ifPresent(minValue -> attr.addAnnotation(Min.class).getRoasterAnnotation().setLiteralValue(String.valueOf(minValue)));
			plantField.getMaxArrayLength().ifPresent(maxValue -> attr.addAnnotation(Max.class).getRoasterAnnotation().setLiteralValue(String.valueOf(maxValue)));
		} else if (attr.isBigDecimalField()) {
			plantField.getMinArrayLength().ifPresent(minValue -> attr.addAnnotation(Min.class).getRoasterAnnotation().setLiteralValue(String.valueOf(minValue)));
			plantField.getMaxArrayLength().ifPresent(maxValue -> attr.addAnnotation(Max.class).getRoasterAnnotation().setLiteralValue(String.valueOf(maxValue)));
		}

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(JavaFieldNotNullType notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldTransientType notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldUniqueType notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToMany() && !umlRelationship.getSourceMultiplicity().isOptional()) {
//			javaField.addSizeAnnotation(1, null);
		}
	}

	@Override
	public void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldManyToManyType manyToManyField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipComposition composition) {

	}

	@Override
	public void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipComposition composition) {

	}

	@Override
	public void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipComposition composition) {

	}

}
