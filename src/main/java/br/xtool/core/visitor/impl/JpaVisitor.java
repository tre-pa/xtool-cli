package br.xtool.core.visitor.impl;

import static br.xtool.core.util.RoasterUtil.findAnnotationOrCreate;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EJavaEntity;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.visitor.Visitor;

public class JpaVisitor implements Visitor {

	private JavaClassSource javaClassSource;

	public JpaVisitor(JavaClassSource javaClassSource) {
		super();
		this.javaClassSource = javaClassSource;
	}

	@Override
	public void visit(EUmlClass umlClass) {
		findAnnotationOrCreate(this.javaClassSource, Entity.class);
		findAnnotationOrCreate(this.javaClassSource, DynamicUpdate.class);
		findAnnotationOrCreate(this.javaClassSource, DynamicInsert.class);
		// @formatter:off
		findAnnotationOrCreate(this.javaClassSource, Table.class)
			.setStringValue("name", EJavaEntity.genDBTableName(umlClass.getName()));
		// @formatter:on
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
