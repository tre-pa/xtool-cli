package br.xtool.core.visitor.impl;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.visitor.Visitor;
import lombok.val;

@Component
public class JacksonVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass, EUmlClass umlClass) {

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
