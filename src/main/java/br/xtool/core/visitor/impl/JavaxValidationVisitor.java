package br.xtool.core.visitor.impl;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.visitor.Visitor;

@Component
public class JavaxValidationVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass, EUmlClass umlClass) {

	}

	@Override
	public void visit(EJavaClass javaClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EJavaField javaField, EUmlField umlField) {
		switch (umlField.getType()) {
		case STRING:
			javaField.addSizeAnnotation(umlField.getMinArrayLength().orElse(null), umlField.getMaxArrayLength().orElse(255));
			break;

		default:
			break;
		}
	}

	@Override
	public void visit(EJavaField javaField, EUmlFieldProperty umlFieldProperty) {

	}

	@Override
	public void visit(EJavaField javaField, EUmlRelationship umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToMany() && !umlRelationship.getSourceMultiplicity().isOptional()) {
			javaField.addSizeAnnotation(1, null);
		}
	}

}
