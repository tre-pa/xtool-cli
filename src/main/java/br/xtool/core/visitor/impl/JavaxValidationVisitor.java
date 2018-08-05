package br.xtool.core.visitor.impl;

import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.visitor.Visitor;
import lombok.val;

@Component
public class JavaxValidationVisitor implements Visitor {

	@Override
	public void visit(EUmlClass umlClass) {

	}

	@Override
	public void visit(EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EJavaField javaField, EUmlField umlField) {
		switch (umlField.getType()) {
		case STRING:
			if (umlField.getMaxArrayLength().isPresent() || umlField.getMinArrayLength().isPresent()) {
				val ann = javaField.addAnnotation(Size.class);
				umlField.getMinArrayLength().ifPresent(min -> ann.setLiteralValue("min", String.valueOf(min)));
				umlField.getMaxArrayLength().ifPresent(max -> ann.setLiteralValue("max", String.valueOf(max)));
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void visit(EUmlFieldProperty umlFieldProperty) {

	}

	@Override
	public void visit(EUmlRelationship umlRelationship) {

	}

}
