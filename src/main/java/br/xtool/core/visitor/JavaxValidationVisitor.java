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
import br.xtool.core.representation.springboot.JavaClassRepresentation;
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
	public void visit(JavaClassRepresentation javaClass, PlantClassRepresentation umlClass) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation plantField) {
		if (javaField.isStringField()) {
			var ann = javaField.getRoasterField().addAnnotation(Size.class);
			plantField.getMinArrayLength().ifPresent(v -> ann.setLiteralValue("min", String.valueOf(v)));
			ann.setLiteralValue("max", String.valueOf(plantField.getMaxArrayLength().orElse(255)));
		} else if (javaField.isNumberField()) {
			plantField.getMinArrayLength().ifPresent(minValue -> javaField.addAnnotation(Min.class).getRoasterAnnotation().setLiteralValue(String.valueOf(minValue)));
			plantField.getMaxArrayLength().ifPresent(maxValue -> javaField.addAnnotation(Max.class).getRoasterAnnotation().setLiteralValue(String.valueOf(maxValue)));
		} else if (javaField.isBigDecimalField()) {
			plantField.getMinArrayLength().ifPresent(minValue -> javaField.addAnnotation(Min.class).getRoasterAnnotation().setLiteralValue(String.valueOf(minValue)));
			plantField.getMaxArrayLength().ifPresent(maxValue -> javaField.addAnnotation(Max.class).getRoasterAnnotation().setLiteralValue(String.valueOf(maxValue)));
		}

	}

//	@Override
//	public void visit(JavaFieldStringType stringField, PlantClassFieldRepresentation umlField) {
//		// @formatter:off
//		stringField.addSizeAnnotation(
//				umlField.getMinArrayLength().orElse(null), 
//				umlField.getMaxArrayLength().orElse(255));
//		// @formatter:on
//	}

//	@Override
//	public void visit(JavaFieldBooleanType booleanField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldLongType longField, PlantClassFieldRepresentation umlField) {
//		umlField.getMinArrayLength().ifPresent(minValue -> longField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
//		umlField.getMaxArrayLength().ifPresent(maxValue -> longField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
//	}

//	@Override
//	public void visit(JavaFieldIntegerType integerField, PlantClassFieldRepresentation umlField) {
//		umlField.getMinArrayLength().ifPresent(minValue -> integerField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
//		umlField.getMaxArrayLength().ifPresent(maxValue -> integerField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
//	}

//	@Override
//	public void visit(JavaFieldByteType byteField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldBigDecimalType bigDecimalField, PlantClassFieldRepresentation umlField) {
//		umlField.getMinArrayLength().ifPresent(minValue -> bigDecimalField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
//		umlField.getMaxArrayLength().ifPresent(maxValue -> bigDecimalField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
//	}

//	@Override
//	public void visit(JavaFieldLocalDateType localDateField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldLocalDateTimeType localDateTimeField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldEnumType enumField, PlantClassFieldRepresentation umlField) {
//		// TODO Auto-generated method stub
//
//	}

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
