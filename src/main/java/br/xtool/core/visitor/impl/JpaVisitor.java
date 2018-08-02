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

import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.visitor.Visitor;

@Deprecated
public class JpaVisitor implements Visitor {

	private JavaClassSource javaClassSource;

	public JpaVisitor(JavaClassSource javaClassSource) {
		super();
		this.javaClassSource = javaClassSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitClass(br.xtool.core.representation.EUmlClass)
	 */
	@Override
	public void visitClass(EUmlClass umlClass) {
		// @formatter:off
		this.javaClassSource.addAnnotation(Entity.class)
				.getOrigin()
			.addAnnotation(DynamicUpdate.class)
				.getOrigin()
			.addAnnotation(DynamicInsert.class)
				.getOrigin()
			.addAnnotation(Table.class)
				.setStringValue("name", EJpaEntity.genDBTableName(umlClass.getName()));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitIdField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitIdField(EUmlField umlField) {
		// @formatter:off
		this.javaClassSource.getField(umlField.getName())
			.addAnnotation(Id.class)
				.getOrigin()
			.getField(umlField.getName())
				.addAnnotation(GeneratedValue.class)
				.setEnumValue("strategy", GenerationType.SEQUENCE)
				.setStringValue("generator", EJpaEntity.genDBSequenceName(this.javaClassSource.getName()))
					.getOrigin()
			.getField(umlField.getName())
				.addAnnotation(SequenceGenerator.class)
				.setLiteralValue("initialValue", "1")
				.setLiteralValue("allocationSize", "1")
				.setStringValue("name", EJpaEntity.genDBSequenceName(this.javaClassSource.getName()))
				.setStringValue("sequenceName", EJpaEntity.genDBSequenceName(this.javaClassSource.getName()));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitLongField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitLongField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitBooleanField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitBooleanField(EUmlField umlField) {
		FieldSource<JavaClassSource> booleanField = this.javaClassSource.getField(umlField.getName());
		booleanField.addAnnotation(Column.class);
		booleanField.setLiteralInitializer("false");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitStringField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitStringField(EUmlField umlField) {
		FieldSource<JavaClassSource> stringField = this.javaClassSource.getField(umlField.getName());
		stringField.addAnnotation(Column.class).setLiteralValue("length", String.valueOf(umlField.getMaxArrayLength().orElse(255)));
		if (umlField.getMinArrayLength().isPresent() || umlField.getMaxArrayLength().isPresent()) {
			AnnotationSource<JavaClassSource> sizeAnnotation = stringField.addAnnotation(Size.class);
			umlField.getMinArrayLength().ifPresent(min -> sizeAnnotation.setLiteralValue("min", String.valueOf(min)));
			umlField.getMaxArrayLength().ifPresent(max -> sizeAnnotation.setLiteralValue("max", String.valueOf(max)));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitIntegerField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitIntegerField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitBigDecimalField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitBigDecimalField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitByteArrayField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitByteArrayField(EUmlField umlField) {

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitLocalDateField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitLocalDateField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitLocalDateTimeField(br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visitLocalDateTimeField(EUmlField umlField) {
		this.javaClassSource.getField(umlField.getName()).addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitUniqueProperty(br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visitUniqueProperty(EUmlFieldProperty umlFieldProperty) {
		this.javaClassSource.getField(umlFieldProperty.getField().getName()).getAnnotation(Column.class).setLiteralValue("unique", "true");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitNotNullProperty(br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visitNotNullProperty(EUmlFieldProperty umlFieldProperty) {
		this.javaClassSource.getField(umlFieldProperty.getField().getName()).getAnnotation(Column.class).setLiteralValue("nullable", "false");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visitTransientProperty(br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visitTransientProperty(EUmlFieldProperty umlFieldProperty) {
		this.javaClassSource.getField(umlFieldProperty.getField().getName()).addAnnotation(Transient.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EUmlRelationship)
	 */
	@Override
	public void visit(EUmlRelationship umlRelationship) {

	}

}
