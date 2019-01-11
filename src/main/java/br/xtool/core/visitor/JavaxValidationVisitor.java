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
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.EAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.EComposition;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EAuditableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.ECacheableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EIndexedJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EReadOnlyJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EVersionableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EViewJavaClass;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EBigDecimalField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EBooleanField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EByteField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EEnumField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EIntegerField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ELocalDateField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ELocalDateTimeField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ELongField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EManyToManyField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EManyToOneField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ENotNullField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EOneToManyField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EOneToOneField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EStringField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ETransientField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EUniqueField;

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
	public void visit(EStringField stringField, PlantClassFieldRepresentation umlField) {
		// @formatter:off
		stringField.addSizeAnnotation(
				umlField.getMinArrayLength().orElse(null), 
				umlField.getMaxArrayLength().orElse(255));
		// @formatter:on
	}

	@Override
	public void visit(EBooleanField booleanField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELongField longField, PlantClassFieldRepresentation umlField) {
		umlField.getMinArrayLength().ifPresent(minValue -> longField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
		umlField.getMaxArrayLength().ifPresent(maxValue -> longField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
	}

	@Override
	public void visit(EIntegerField integerField, PlantClassFieldRepresentation umlField) {
		umlField.getMinArrayLength().ifPresent(minValue -> integerField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
		umlField.getMaxArrayLength().ifPresent(maxValue -> integerField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
	}

	@Override
	public void visit(EByteField byteField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EBigDecimalField bigDecimalField, PlantClassFieldRepresentation umlField) {
		umlField.getMinArrayLength().ifPresent(minValue -> bigDecimalField.addAnnotation(Min.class).setLiteralValue(String.valueOf(minValue)));
		umlField.getMaxArrayLength().ifPresent(maxValue -> bigDecimalField.addAnnotation(Max.class).setLiteralValue(String.valueOf(maxValue)));
	}

	@Override
	public void visit(ELocalDateField localDateField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELocalDateTimeField localDateTimeField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EEnumField enumField, PlantClassFieldRepresentation umlField) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(ENotNullField notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(ETransientField notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(EUniqueField notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToMany() && !umlRelationship.getSourceMultiplicity().isOptional()) {
			javaField.addSizeAnnotation(1, null);
		}
	}

	@Override
	public void visit(EOneToOneField oneToOneField, EAssociation association) {

	}

	@Override
	public void visit(EOneToManyField oneToManyField, EAssociation association) {

	}

	@Override
	public void visit(EManyToOneField manyToOneField, EAssociation association) {

	}

	@Override
	public void visit(EManyToManyField manyToManyField, EAssociation association) {

	}

	@Override
	public void visit(EOneToOneField oneToOneField, EComposition composition) {

	}

	@Override
	public void visit(EOneToManyField oneToManyField, EComposition composition) {

	}

	@Override
	public void visit(EManyToOneField manyToOneField, EComposition composition) {

	}

}
