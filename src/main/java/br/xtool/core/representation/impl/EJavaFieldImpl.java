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

import br.xtool.core.representation.JavaAnnotationRepresentation;
import br.xtool.core.representation.JavaClassRepresentation;
import br.xtool.core.representation.JavaEnumRepresentation;
import br.xtool.core.representation.JavaFieldRepresentation;
import br.xtool.core.representation.JavaRelationshipRepresentation;
import br.xtool.core.representation.EntityRepresentation;

public class EJavaFieldImpl implements JavaFieldRepresentation {

	private JavaClassRepresentation javaClass;

	protected FieldSource<JavaClassSource> fieldSource;

	public EJavaFieldImpl(JavaClassRepresentation javaClass, FieldSource<JavaClassSource> fieldSource) {
		super();
		this.javaClass = javaClass;
		this.fieldSource = fieldSource;
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
	public JavaClassRepresentation getJavaClass() {
		return this.javaClass;
	}

	@Override
	public Optional<JavaEnumRepresentation> getEnum() {
		// @formatter:off
		return this.getJavaClass().getProject().getEnums().stream()
				.filter(javaEnum -> javaEnum.getName().equals(this.getType().getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public boolean isEnumField() {
		return this.getEnum().isPresent();
	}

	@Override
	public boolean isRelationshipField() {
		return this.getRelationship().isPresent();
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
	public SortedSet<JavaAnnotationRepresentation<JavaClassSource>> getAnnotations() {
		// @formatter:off
		return this.fieldSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public JavaAnnotationRepresentation<JavaClassSource> addAnnotation(Class<? extends Annotation> type) {
		// @formatter:off
		return this.getAnnotations().stream()
				.filter(javaAnn -> javaAnn.getName().equals(type.getSimpleName()))
				.findAny()
				.orElseGet(() -> new EJavaAnnotationImpl<JavaClassSource>(this.fieldSource.addAnnotation(type)));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJavaField#getRoasterField()
	 */
	@Override
	public FieldSource<JavaClassSource> getRoasterField() {
		return this.fieldSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJavaField#getRelationship()
	 */
	@Override
	public Optional<JavaRelationshipRepresentation> getRelationship() {
		if (this.isCollection()) {
			String entityName = Types.getGenericsTypeParameter(this.getType().getQualifiedNameWithGenerics());
			// @formatter:off
			return this.getJavaClass().getProject().getEntities().stream()
					.filter(entity -> entity.getName().equals(entityName))
					.map(entityTarget -> new EJavaRelationshipImpl(this.javaClass, entityTarget, this))
					.map(JavaRelationshipRepresentation.class::cast)
					.findFirst();
			// @formatter:on
		}
		// @formatter:off
		String entityName = this.getType().getName();
		return this.getJavaClass().getProject().getEntities().stream()
				.filter(entity -> entity.getName().equals(entityName))
				.map(entityTarget -> new EJavaRelationshipImpl(this.javaClass, entityTarget, this))
				.map(JavaRelationshipRepresentation.class::cast)
				.findFirst();
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.representation.EJavaField#addSizeAnnotation(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public JavaAnnotationRepresentation<JavaClassSource> addSizeAnnotation(Integer min, Integer max) {
		JavaAnnotationRepresentation<JavaClassSource> ann = this.addAnnotation(Size.class);
		if (Objects.nonNull(min)) ann.setLiteralValue("min", String.valueOf(min));
		if (Objects.nonNull(max)) ann.setLiteralValue("max", String.valueOf(max));
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.representation.EJavaField#addBatchSizeAnnotation(java.lang.
	 * Integer)
	 */
	@Override
	public JavaAnnotationRepresentation<JavaClassSource> addBatchSizeAnnotation(Integer size) {
		JavaAnnotationRepresentation<JavaClassSource> ann = this.addAnnotation(BatchSize.class);
		ann.setLiteralValue("size", String.valueOf(size));
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJavaField#addLazyCollectionAnnotation(org.
	 * hibernate.annotations.LazyCollectionOption)
	 */
	@Override
	public JavaAnnotationRepresentation<JavaClassSource> addLazyCollectionAnnotation(LazyCollectionOption lazyCollectionOption) {
		JavaAnnotationRepresentation<JavaClassSource> ann = this.addAnnotation(LazyCollection.class);
		ann.setEnumValue(lazyCollectionOption);
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.representation.EJavaField#addGeneratedValueAnnotation(javax.
	 * persistence.GenerationType)
	 */
	@Override
	public JavaAnnotationRepresentation<JavaClassSource> addGeneratedValueAnnotation(GenerationType generationType) {
		JavaAnnotationRepresentation<JavaClassSource> ann = this.addAnnotation(GeneratedValue.class);
		ann.setEnumValue("strategy", GenerationType.SEQUENCE).setStringValue("generator", EntityRepresentation.genDBSequenceName(this.getJavaClass().getName()));
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJavaField#addSequenceGeneratorAnnotation()
	 */
	@Override
	public JavaAnnotationRepresentation<JavaClassSource> addSequenceGeneratorAnnotation() {
		JavaAnnotationRepresentation<JavaClassSource> ann = this.addAnnotation(SequenceGenerator.class);
		// @formatter:off
		ann.setLiteralValue("initialValue", "1")
			.setLiteralValue("allocationSize", "1")
			.setStringValue("name", EntityRepresentation.genDBSequenceName(this.getJavaClass().getName()))
			.setStringValue("sequenceName", EntityRepresentation.genDBSequenceName(this.getJavaClass().getName()));
		// @formatter:on
		return ann;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(JavaFieldRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

	public static class EStringFieldImpl extends EJavaFieldImpl implements EStringField {
		public EStringFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}
	}

	public static class EBooleanFieldImpl extends EJavaFieldImpl implements EBooleanField {
		public EBooleanFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ELongFieldImpl extends EJavaFieldImpl implements ELongField {
		public ELongFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EIntegerFieldImpl extends EJavaFieldImpl implements EIntegerField {
		public EIntegerFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EByteFieldImpl extends EJavaFieldImpl implements JavaFieldRepresentation {
		public EByteFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EBigDecimalFieldImpl extends EJavaFieldImpl implements EBigDecimalField {
		public EBigDecimalFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ELocalDateFieldImpl extends EJavaFieldImpl implements ELocalDateField {
		public ELocalDateFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ELocalDateTimeFieldImpl extends EJavaFieldImpl implements ELocalDateTimeField {
		public ELocalDateTimeFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EENumFieldImpl extends EJavaFieldImpl implements EEnumField {
		public EENumFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ENotNullFieldImpl extends EJavaFieldImpl implements ENotNullField {
		public ENotNullFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class ETransientFieldImpl extends EJavaFieldImpl implements ETransientField {
		public ETransientFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EUniqueFieldImpl extends EJavaFieldImpl implements EUniqueField {
		public EUniqueFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EOneToOneFieldImpl extends EJavaFieldImpl implements EOneToOneField {
		public EOneToOneFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EOneToManyFieldImpl extends EJavaFieldImpl implements EOneToManyField {
		public EOneToManyFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EManyToOneFieldImpl extends EJavaFieldImpl implements EManyToOneField {
		public EManyToOneFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

	public static class EManyToManyFieldImpl extends EJavaFieldImpl implements EManyToManyField {
		public EManyToManyFieldImpl(JavaFieldRepresentation javaField) {
			super(javaField.getJavaClass(), javaField.getRoasterField());
		}

	}

}
