package br.xtool.core.visitor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipComposition;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EAuditableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.ECacheableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EIndexedJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EReadOnlyJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EVersionableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EViewJavaClass;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldBigDecimalType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldBooleanType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldByteType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldEnumType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldIntegerType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldLocalDateType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldLocalDateTimeType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldLongType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldNotNullType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldStringType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldTransientType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldUniqueType;

@Component
public class JavaxValidationVisitor implements Visitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass)
	 */
	@Override
	public void visit(JavaClassRepresentation javaClass, PlantClassRepresentation umlClass) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass,
	 * br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(JavaClassRepresentation javaClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EAuditableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EAuditableJavaClass auditableClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * ECacheableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(ECacheableJavaClass cacheableClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EIndexedJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EIndexedJavaClass indexedClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EViewJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EViewJavaClass viewClass, PlantStereotypeRepresentation umlStereotype) {

	}

	@Override
	public void visit(EReadOnlyJavaClass readOnlyClass, PlantStereotypeRepresentation umlStereotype) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EVersionableJavaClass versionableClass, PlantStereotypeRepresentation umlStereotype) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField,
	 * br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation umlField) {
	}

	@Override
	public void visit(JavaFieldStringType stringField, PlantClassFieldRepresentation umlField) {
		// @formatter:off
		stringField.addSizeAnnotation(
				umlField.getMinArrayLength().orElse(null), 
				umlField.getMaxArrayLength().orElse(255));
		// @formatter:on
	}

	@Override
	public void visit(JavaFieldBooleanType booleanField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldLongType longField, PlantClassFieldRepresentation umlField) {
		umlField.getMinArrayLength().ifPresent(minValue -> longField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
		umlField.getMaxArrayLength().ifPresent(maxValue -> longField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
	}

	@Override
	public void visit(JavaFieldIntegerType integerField, PlantClassFieldRepresentation umlField) {
		umlField.getMinArrayLength().ifPresent(minValue -> integerField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
		umlField.getMaxArrayLength().ifPresent(maxValue -> integerField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
	}

	@Override
	public void visit(JavaFieldByteType byteField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldBigDecimalType bigDecimalField, PlantClassFieldRepresentation umlField) {
		umlField.getMinArrayLength().ifPresent(minValue -> bigDecimalField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
		umlField.getMaxArrayLength().ifPresent(maxValue -> bigDecimalField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
	}

	@Override
	public void visit(JavaFieldLocalDateType localDateField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldLocalDateTimeType localDateTimeField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldEnumType enumField, PlantClassFieldRepresentation umlField) {
		// TODO Auto-generated method stub

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
			javaField.addSizeAnnotation(1, null);
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
