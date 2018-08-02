package br.xtool.core.visitor.jpa.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.visitor.ClassVisitor;

@Component
public class JpaClassVisitor implements ClassVisitor {

	@Override
	public boolean test(EUmlClass umlClass) {
		return false;
	}

	@Override
	public void accept(JavaClassSource roasterClass, EUmlClass umlClass) {

	}

}
