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

	@Override
	public boolean isFinal() {
		return this.fieldSource.isFinal();
	}

	@Override
	public boolean isPackagePrivate() {
		return this.fieldSource.isPackagePrivate();
	}

	@Override
	public boolean isPublic() {
		return this.fieldSource.isPublic();
	}

	@Override
	public boolean isPrivate() {
		return this.fieldSource.isPrivate();
	}

	@Override
	public boolean isProtected() {
		return this.fieldSource.isProtected();
	}

	@Override
	public boolean hasJavaDoc() {
		return this.fieldSource.hasJavaDoc();
	}

	@Override
	public boolean isTransient() {
		return this.fieldSource.isTransient();
	}

	@Override
	public boolean isVolatile() {
		return this.fieldSource.isVolatile();
	}

	@Override
	public int getLineNumber() {
		return this.fieldSource.getLineNumber();
	}

	@Override
	public FieldSource<JavaClassSource> getRoasterField() {
		return this.fieldSource;
	}

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

	@Override
	public EJavaAnnotation addSize(Integer min, Integer max) {
		EJavaAnnotation ann = this.addAnnotation(Size.class);
		if (Objects.nonNull(min)) ann.setLiteralValue("min", String.valueOf(min));
		if (Objects.nonNull(max)) ann.setLiteralValue("max", String.valueOf(max));
		return ann;
	}

	@Override
	public EJavaAnnotation addBatchSize(Integer size) {
		EJavaAnnotation ann = this.addAnnotation(BatchSize.class);
		ann.setLiteralValue("size", String.valueOf(size));
		return ann;
	}

	@Override
	public EJavaAnnotation addLazyCollection(LazyCollectionOption lazyCollectionOption) {
		EJavaAnnotation ann = this.addAnnotation(LazyCollection.class);
		ann.setEnumValue(lazyCollectionOption);
		return ann;
	}

	@Override
	public EJavaAnnotation addGeneratedValue(GenerationType generationType) {
		EJavaAnnotation ann = this.addAnnotation(GeneratedValue.class);
		ann.setEnumValue("strategy", GenerationType.SEQUENCE).setStringValue("generator", EJpaEntity.genDBSequenceName(this.getJavaClass().getName()));
		return ann;
	}

	@Override
	public EJavaAnnotation addSequenceGenerator() {
		EJavaAnnotation ann = this.addAnnotation(SequenceGenerator.class);
		// @formatter:off
		ann.setLiteralValue("initialValue", "1")
			.setLiteralValue("allocationSize", "1")
			.setStringValue("name", EJpaEntity.genDBSequenceName(this.getJavaClass().getName()))
			.setStringValue("sequenceName", EJpaEntity.genDBSequenceName(this.getJavaClass().getName()));
		// @formatter:on
		return ann;
	}

	@Override
	public int compareTo(EJavaField o) {
		return this.getName().compareTo(o.getName());
	}

}
