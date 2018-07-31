package br.xtool.core.visitor.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
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
		// @formatter:off
		this.javaClassSource.addAnnotation(Entity.class)
				.getOrigin()
			.addAnnotation(DynamicUpdate.class)
				.getOrigin()
			.addAnnotation(DynamicInsert.class)
				.getOrigin()
			.addAnnotation(Table.class)
				.setStringValue("name", EJavaEntity.genDBTableName(umlClass.getName()));
		// @formatter:on
	}

	@Override
	public void visit(EUmlField umlField) {
	}

	@Override
	public void visitIdField(EUmlField umlField) {
		// @formatter:off
		this.javaClassSource.getField(umlField.getName())
			.addAnnotation(Id.class)
				.getOrigin()
			.getField(umlField.getName())
			.addAnnotation(GeneratedValue.class)
			.setEnumValue("strategy", GenerationType.SEQUENCE)
			.setStringValue("generator", EJavaEntity.genDBSequenceName(this.javaClassSource.getName()))
				.getOrigin()
			.getField(umlField.getName())
			.addAnnotation(SequenceGenerator.class)
			.setLiteralValue("initialValue", "1")
			.setLiteralValue("allocationSize", "1")
			.setStringValue("name", EJavaEntity.genDBSequenceName(this.javaClassSource.getName()))
			.setStringValue("sequenceName", EJavaEntity.genDBSequenceName(this.javaClassSource.getName()));
		// @formatter:on
	}

	@Override
	public void visitLongField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	@Override
	public void visitBooleanField(EUmlField umlField) {
		FieldSource<JavaClassSource> booleanField = this.javaClassSource.getField(umlField.getName());
		booleanField.addAnnotation(Column.class);
		booleanField.setLiteralInitializer("false");
	}

	@Override
	public void visitStringField(EUmlField umlField) {
		FieldSource<JavaClassSource> stringField = this.javaClassSource.getField(umlField.getName());
		AnnotationSource<JavaClassSource> columnAnnotation = stringField.addAnnotation(Column.class);
		umlField.getMaxArrayLength().ifPresent(max -> columnAnnotation.setLiteralValue("length", String.valueOf(max)));
		if (umlField.getMinArrayLength().isPresent() || umlField.getMaxArrayLength().isPresent()) {
			AnnotationSource<JavaClassSource> sizeAnnotation = stringField.addAnnotation(Size.class);
			umlField.getMinArrayLength().ifPresent(min -> sizeAnnotation.setLiteralValue("min", String.valueOf(min)));
			umlField.getMaxArrayLength().ifPresent(max -> sizeAnnotation.setLiteralValue("max", String.valueOf(max)));
		}
	}

	@Override
	public void visitIntegerField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	@Override
	public void visitBigDecimalField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	@Override
	public void visitByteArrayField(EUmlField umlField) {

	}

	@Override
	public void visitLocalDateField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	@Override
	public void visitLocalDateTimeField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	@Override
	public void visitUniqueProperty(EUmlFieldProperty umlFieldProperty) {
		this.javaClassSource.getField(umlFieldProperty.getField().getName()).getAnnotation(Column.class).setLiteralValue("unique", "true");
	}

	@Override
	public void visitNotNullProperty(EUmlFieldProperty umlFieldProperty) {
		this.javaClassSource.getField(umlFieldProperty.getField().getName()).getAnnotation(Column.class).setLiteralValue("nullable", "false");
	}

	@Override
	public void visitTransientProperty(EUmlFieldProperty umlFieldProperty) {
		this.javaClassSource.getField(umlFieldProperty.getField().getName()).addAnnotation(Transient.class);
	}

	@Override
	public void visit(EUmlRelationship umlRelationship) {

	}

}
