package br.xtool.core.visitor.impl;

import javax.persistence.Entity;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.util.RoasterUtil;
import br.xtool.core.visitor.Visitor;

public class JpaVisitor implements Visitor {

	private JavaClassSource javaClassSource;

	public JpaVisitor(JavaClassSource javaClassSource) {
		super();
		this.javaClassSource = javaClassSource;
	}

	@Override
	public void visit(EUmlClass umlClass) {
		RoasterUtil.findAnnotationOrCreate(this.javaClassSource, Entity.class);
	}

	@Override
	public void visit(EUmlField umlField) {

	}

	@Override
	public void visit(EUmlFieldProperty umlFieldProperty) {

	}

	@Override
	public void visit(EUmlRelationship umlRelationship) {
	}

}
