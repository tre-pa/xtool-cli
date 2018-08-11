package br.xtool.core.representation.impl;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaRelationship;
import br.xtool.core.representation.EJpaEntity;

public class EJavaFieldImpl implements EJavaField {

	private EJavaClass javaClass;

	protected FieldSource<JavaClassSource> fieldSource;

	private EBootProject bootProject;

	public EJavaFieldImpl(EBootProject bootProject, EJavaClass javaClass, FieldSource<JavaClassSource> fieldSource) {
		super();
		this.bootProject = bootProject;
		this.javaClass = javaClass;
		this.fieldSource = fieldSource;
	}

	@Override
	public EBootProject getProject() {
		return this.bootProject;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.fieldSource.getName();
	}

	@Override
	public EJavaClass getJavaClass() {
		return this.javaClass;
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	@Override
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}

	/**
	 * Verifica se o atributo é uma coleção
	 * 
	 * @return
	 */
	@Override
	public boolean isCollection() {
		return this.fieldSource.getType().isType(List.class);
	}

	/**
	 * Verifica se o atributo é static.
	 * 
	 * @return
	 */
	@Override
	public boolean isStatic() {
		return this.fieldSource.isStatic();
	}

	@Override
	public SortedSet<EJavaAnnotation> getAnnotations() {
		// @formatter:off
		return this.fieldSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public EJavaAnnotation addAnnotation(Class<? extends Annotation> type) {
		// @formatter:off
		return this.getAnnotations().stream()
				.filter(javaAnn -> javaAnn.getName().equals(type.getSimpleName()))
				.findAny()
				.orElseGet(() -> new EJavaAnnotationImpl(this.fieldSource.addAnnotation(type)));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaField#getRoasterField()
	 */
	@Override
	public FieldSource<JavaClassSource> getRoasterField() {
		return this.fieldSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaField#getRelationship()
	 */
	@Override
	public Optional<EJavaRelationship> getRelationship() {
		if (this.isCollection()) {
			String entityName = Types.getGenericsTypeParameter(this.getType().getQualifiedNameWithGenerics());
			// @formatter:off
			return this.bootProject.getEntities().stream()
					.filter(entity -> entity.getName().equals(entityName))
					.map(entityTarget -> new EJavaRelationshipImpl(this.javaClass, entityTarget, this))
					.map(EJavaRelationship.class::cast)
					.findFirst();
			// @formatter:on
		}
		// @formatter:off
		String entityName = this.getType().getName();
		return this.bootProject.getEntities().stream()
				.filter(entity -> entity.getName().equals(entityName))
				.map(entityTarget -> new EJavaRelationshipImpl(this.javaClass, entityTarget, this))
				.map(EJavaRelationship.class::cast)
				.findFirst();
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaField#addSizeAnnotation(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public EJavaAnnotation addSizeAnnotation(Integer min, Integer max) {
		EJavaAnnotation ann = this.addAnnotation(Size.class);
		if (Objects.nonNull(min)) ann.setLiteralValue("min", String.valueOf(min));
		if (Objects.nonNull(max)) ann.setLiteralValue("max", String.valueOf(max));
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaField#addBatchSizeAnnotation(java.lang.Integer)
	 */
	@Override
	public EJavaAnnotation addBatchSizeAnnotation(Integer size) {
		EJavaAnnotation ann = this.addAnnotation(BatchSize.class);
		ann.setLiteralValue("size", String.valueOf(size));
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaField#addLazyCollectionAnnotation(org.hibernate.annotations.LazyCollectionOption)
	 */
	@Override
	public EJavaAnnotation addLazyCollectionAnnotation(LazyCollectionOption lazyCollectionOption) {
		EJavaAnnotation ann = this.addAnnotation(LazyCollection.class);
		ann.setEnumValue(lazyCollectionOption);
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaField#addGeneratedValueAnnotation(javax.persistence.GenerationType)
	 */
	@Override
	public EJavaAnnotation addGeneratedValueAnnotation(GenerationType generationType) {
		EJavaAnnotation ann = this.addAnnotation(GeneratedValue.class);
		ann.setEnumValue("strategy", GenerationType.SEQUENCE).setStringValue("generator", EJpaEntity.genDBSequenceName(this.getJavaClass().getName()));
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaField#addSequenceGeneratorAnnotation()
	 */
	@Override
	public EJavaAnnotation addSequenceGeneratorAnnotation() {
		EJavaAnnotation ann = this.addAnnotation(SequenceGenerator.class);
		// @formatter:off
		ann.setLiteralValue("initialValue", "1")
			.setLiteralValue("allocationSize", "1")
			.setStringValue("name", EJpaEntity.genDBSequenceName(this.getJavaClass().getName()))
			.setStringValue("sequenceName", EJpaEntity.genDBSequenceName(this.getJavaClass().getName()));
		// @formatter:on
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(EJavaField o) {
		return this.getName().compareTo(o.getName());
	}

	public static class EStringFieldImpl extends EJavaFieldImpl implements EStringField {
		public EStringFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}
	}

	public static class EBooleanFieldImpl extends EJavaFieldImpl implements EBooleanField {
		public EBooleanFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ELongFieldImpl extends EJavaFieldImpl implements ELongField {
		public ELongFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EIntegerFieldImpl extends EJavaFieldImpl implements EIntegerField {
		public EIntegerFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EByteFieldImpl extends EJavaFieldImpl implements EJavaField {
		public EByteFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EBigDecimalFieldImpl extends EJavaFieldImpl implements EBigDecimalField {
		public EBigDecimalFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ELocalDateFieldImpl extends EJavaFieldImpl implements ELocalDateField {
		public ELocalDateFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ELocalDateTimeFieldImpl extends EJavaFieldImpl implements ELocalDateTimeField {
		public ELocalDateTimeFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ENotNullFieldImpl extends EJavaFieldImpl implements ENotNullField {
		public ENotNullFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ETransientFieldImpl extends EJavaFieldImpl implements ETransientField {
		public ETransientFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EUniqueFieldImpl extends EJavaFieldImpl implements EUniqueField {
		public EUniqueFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EOneToOneFieldImpl extends EJavaFieldImpl implements EOneToOneField {
		public EOneToOneFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EOneToManyFieldImpl extends EJavaFieldImpl implements EOneToManyField {
		public EOneToManyFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EManyToOneFieldImpl extends EJavaFieldImpl implements EManyToOneField {
		public EManyToOneFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EManyToManyFieldImpl extends EJavaFieldImpl implements EManyToManyField {
		public EManyToManyFieldImpl(EJavaField javaField) {
			super(javaField.getProject(), javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

}
