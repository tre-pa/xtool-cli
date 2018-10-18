package br.xtool.core.visitor.impl;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaClass.EAuditableJavaClass;
import br.xtool.core.representation.EJavaClass.ECacheableJavaClass;
import br.xtool.core.representation.EJavaClass.EIndexedJavaClass;
import br.xtool.core.representation.EJavaClass.EReadOnlyJavaClass;
import br.xtool.core.representation.EJavaClass.EVersionableJavaClass;
import br.xtool.core.representation.EJavaClass.EViewJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaField.EBigDecimalField;
import br.xtool.core.representation.EJavaField.EBooleanField;
import br.xtool.core.representation.EJavaField.EByteField;
import br.xtool.core.representation.EJavaField.EEnumField;
import br.xtool.core.representation.EJavaField.EIntegerField;
import br.xtool.core.representation.EJavaField.ELocalDateField;
import br.xtool.core.representation.EJavaField.ELocalDateTimeField;
import br.xtool.core.representation.EJavaField.ELongField;
import br.xtool.core.representation.EJavaField.EManyToManyField;
import br.xtool.core.representation.EJavaField.EManyToOneField;
import br.xtool.core.representation.EJavaField.ENotNullField;
import br.xtool.core.representation.EJavaField.EOneToManyField;
import br.xtool.core.representation.EJavaField.EOneToOneField;
import br.xtool.core.representation.EJavaField.EStringField;
import br.xtool.core.representation.EJavaField.ETransientField;
import br.xtool.core.representation.EJavaField.EUniqueField;
import br.xtool.core.representation.EPlantClass;
import br.xtool.core.representation.EPlantField;
import br.xtool.core.representation.EPlantFieldProperty;
import br.xtool.core.representation.EPlantRelationship;
import br.xtool.core.representation.EPlantRelationship.EAssociation;
import br.xtool.core.representation.EPlantRelationship.EComposition;
import br.xtool.core.representation.EPlantStereotype;
import br.xtool.core.visitor.Visitor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public class LombokVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass, EPlantClass umlClass) {
		javaClass.addAnnotation(Getter.class);
		javaClass.addAnnotation(Setter.class);
		javaClass.addAnnotation(NoArgsConstructor.class);
		javaClass.addEqualsAndHashCodeAnnotation("id");
		javaClass.addToStringAnnotation("id");
	}

	@Override
	public void visit(EJavaClass javaClass, EPlantStereotype umlStereotype) {

	}

	@Override
	public void visit(EAuditableJavaClass auditableClass, EPlantStereotype umlStereotype) {

	}

	@Override
	public void visit(ECacheableJavaClass cacheableClass, EPlantStereotype umlStereotype) {

	}

	@Override
	public void visit(EIndexedJavaClass indexedClass, EPlantStereotype umlStereotype) {

	}

	@Override
	public void visit(EViewJavaClass viewClass, EPlantStereotype umlStereotype) {

	}

	@Override
	public void visit(EReadOnlyJavaClass readOnlyClass, EPlantStereotype umlStereotype) {

	}

	@Override
	public void visit(EVersionableJavaClass versionableClass, EPlantStereotype umlStereotype) {
	}

	@Override
	public void visit(EJavaField javaField, EPlantField umlField) {

	}

	@Override
	public void visit(EJavaField javaField, EPlantFieldProperty umlFieldProperty) {

	}

	@Override
	public void visit(EStringField stringField, EPlantField umlField) {

	}

	@Override
	public void visit(EBooleanField booleanField, EPlantField umlField) {

	}

	@Override
	public void visit(ELongField longField, EPlantField umlField) {

	}

	@Override
	public void visit(EIntegerField integerField, EPlantField umlField) {

	}

	@Override
	public void visit(EByteField byteField, EPlantField umlField) {

	}

	@Override
	public void visit(EBigDecimalField bigDecimalField, EPlantField umlField) {

	}

	@Override
	public void visit(ELocalDateField localDateField, EPlantField umlField) {

	}

	@Override
	public void visit(ELocalDateTimeField localDateTimeField, EPlantField umlField) {

	}
	
	@Override
	public void visit(EEnumField enumField, EPlantField umlField) {

	}

	@Override
	public void visit(ENotNullField notNullField, EPlantFieldProperty property) {

	}

	@Override
	public void visit(ETransientField notNullField, EPlantFieldProperty property) {

	}

	@Override
	public void visit(EUniqueField notNullField, EPlantFieldProperty property) {

	}

	@Override
	public void visit(EJavaField javaField, EPlantRelationship umlRelationship) {

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
