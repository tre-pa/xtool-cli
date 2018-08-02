package br.xtool.core.visitor.jpa.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EUmlField;
import br.xtool.core.visitor.jpa.JpaFieldVisitor;

@Component
public class JpaIdFieldVisitor implements JpaFieldVisitor {

	@Override
	public boolean test(EUmlField umlField) {
		return umlField.isId();
	}

	@Override
	public void accept(JavaClassSource roasterClass, EUmlField umlField) {

	}

}
