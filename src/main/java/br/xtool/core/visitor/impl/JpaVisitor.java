package br.xtool.core.visitor.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EJavaEntity;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlField.FieldType;
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
		this.javaClassSource.addAnnotation(Entity.class);
		this.javaClassSource.addAnnotation(DynamicUpdate.class);
		this.javaClassSource.addAnnotation(DynamicInsert.class);
		this.javaClassSource.addAnnotation(Table.class).setStringValue("name", EJavaEntity.genDBTableName(umlClass.getName()));
	}

	@Override
	public void visit(EUmlField umlField) {
		mapId(umlField);
		mapLong(umlField);
		mapString(umlField);
	}

	@Override
	public void visit(EUmlFieldProperty umlFieldProperty) {

	}

	@Override
	public void visit(EUmlRelationship umlRelationship) {
	}

	private void mapId(EUmlField umlField) {
		if (umlField.isId()) {
			FieldSource<JavaClassSource> idField = this.javaClassSource.getField(umlField.getName());
			idField.addAnnotation(Id.class);
			// @formatter:off
			idField.addAnnotation(GeneratedValue.class)
			.setEnumValue("strategy", GenerationType.SEQUENCE)
			.setStringValue("generator", EJavaEntity.genDBSequenceName(this.javaClassSource.getName()));
			idField.addAnnotation(SequenceGenerator.class)
			.setLiteralValue("initialValue", "1")
			.setLiteralValue("allocationSize", "1")
			.setStringValue("name", EJavaEntity.genDBSequenceName(this.javaClassSource.getName()))
			.setStringValue("sequenceName", EJavaEntity.genDBSequenceName(this.javaClassSource.getName()));
			// @formatter:on
		}
	}

	private void mapString(EUmlField umlField) {
		if (umlField.getType().equals(FieldType.STRING)) {
			FieldSource<JavaClassSource> stringField = this.javaClassSource.getField(umlField.getName());
			AnnotationSource<JavaClassSource> columnAnnotation = stringField.addAnnotation(Column.class);
			umlField.getMaxArrayLength().ifPresent(max -> columnAnnotation.setLiteralValue("length", String.valueOf(max)));
			//			umlField.getMaxArrayLength().ifPresent(max -> stringField.addAnnotation(Size.class).setLiteralValue("max", String.valueOf(max)));
		}
	}

	private void mapLong(EUmlField umlField) {
		if (!umlField.isId() && umlField.getType().equals(FieldType.LONG)) {
			FieldSource<JavaClassSource> longField = this.javaClassSource.getField(umlField.getName());
			longField.addAnnotation(Column.class);
		}
	}

}
