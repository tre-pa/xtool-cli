package br.xtool.core.visitor.impl;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaClass;
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
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.visitor.Visitor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public class LombokVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass) {
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
	public void visit(EOneToOneField oneToOneField, EUmlRelationship umlRelationship) {

	}

	@Override
	public void visit(EOneToManyField oneToManyField, EUmlRelationship umlRelationship) {

	}

	@Override
	public void visit(EManyToOneField manyToOneField, EUmlRelationship umlRelationship) {

	}

	@Override
	public void visit(EManyToManyField manyToManyField, EUmlRelationship umlRelationship) {

	}

}
