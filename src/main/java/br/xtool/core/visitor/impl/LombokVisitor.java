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
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlRelationship.EAssociation;
import br.xtool.core.representation.EUmlRelationship.EComposition;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.visitor.Visitor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public class LombokVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass, EUmlClass umlClass) {
		javaClass.addAnnotation(Getter.class);
		javaClass.addAnnotation(Setter.class);
		javaClass.addAnnotation(NoArgsConstructor.class);
		javaClass.addEqualsAndHashCodeAnnotation("id");
		javaClass.addToStringAnnotation("id");
	}

	@Override
	public void visit(EJavaClass javaClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EAuditableJavaClass auditableClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(ECacheableJavaClass cacheableClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EIndexedJavaClass indexedClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EViewJavaClass viewClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EReadOnlyJavaClass readOnlyClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EVersionableJavaClass versionableClass, EUmlStereotype umlStereotype) {
	}

	@Override
	public void visit(EJavaField javaField, EUmlField umlField) {

	}

	@Override
	public void visit(EJavaField javaField, EUmlFieldProperty umlFieldProperty) {

	}

	@Override
	public void visit(EStringField stringField, EUmlField umlField) {

	}

	@Override
	public void visit(EBooleanField booleanField, EUmlField umlField) {

	}

	@Override
	public void visit(ELongField longField, EUmlField umlField) {

	}

	@Override
	public void visit(EIntegerField integerField, EUmlField umlField) {

	}

	@Override
	public void visit(EByteField byteField, EUmlField umlField) {

	}

	@Override
	public void visit(EBigDecimalField bigDecimalField, EUmlField umlField) {

	}

	@Override
	public void visit(ELocalDateField localDateField, EUmlField umlField) {

	}

	@Override
	public void visit(ELocalDateTimeField localDateTimeField, EUmlField umlField) {

	}

	@Override
	public void visit(ENotNullField notNullField, EUmlFieldProperty property) {

	}

	@Override
	public void visit(ETransientField notNullField, EUmlFieldProperty property) {

	}

	@Override
	public void visit(EUniqueField notNullField, EUmlFieldProperty property) {

	}

	@Override
	public void visit(EJavaField javaField, EUmlRelationship umlRelationship) {

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
