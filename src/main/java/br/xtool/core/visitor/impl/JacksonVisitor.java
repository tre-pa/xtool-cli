package br.xtool.core.visitor.impl;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaField.EBigDecimalField;
import br.xtool.core.representation.EJavaField.EBooleanField;
import br.xtool.core.representation.EJavaField.EByteField;
import br.xtool.core.representation.EJavaField.EIntegerField;
import br.xtool.core.representation.EJavaField.ELocalDateField;
import br.xtool.core.representation.EJavaField.ELocalDateTimeField;
import br.xtool.core.representation.EJavaField.ELongField;
import br.xtool.core.representation.EJavaField.EStringField;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.visitor.Visitor;
import lombok.val;

@Component
public class JacksonVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass) {

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
	public void visit(EJavaField javaField, EUmlRelationship umlRelationship) {
		// @formatter:off
		String[] relationships = umlRelationship.getTargetClass().getRelationships().stream()
				.map(_umlRelationship -> _umlRelationship.getSourceRole())
				.toArray(String[]::new);
		// @formatter:on
		if (relationships.length > 0) {
			val ann = javaField.addAnnotation(JsonIgnoreProperties.class);
			ann.setLiteralValue("allowSetters", "true");
			ann.setStringArrayValue("value", relationships);
		}
	}

}
