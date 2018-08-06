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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
public class LombokVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass, EUmlClass umlClass) {
		javaClass.addAnnotation(Getter.class);
		javaClass.addAnnotation(Setter.class);
		javaClass.addAnnotation(EqualsAndHashCode.class).setStringArrayValue("of", new String[] { "id" });
		javaClass.addAnnotation(ToString.class).setStringArrayValue("of", new String[] { "id" });
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

	}

}
