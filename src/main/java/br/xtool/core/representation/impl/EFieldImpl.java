package br.xtool.core.representation.impl;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.EAnnotation;
import br.xtool.core.representation.EField;

public class EFieldImpl implements EField {

	protected FieldSource<JavaClassSource> fieldSource;

	public EFieldImpl(FieldSource<JavaClassSource> fieldSource) {
		super();
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
		return Stream.of("List", "Set", "Collection").peek(type -> this.getType().getName()).anyMatch(type -> type.equals(this.getType().getName()));
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
	public boolean hasAnnotation(String name) {
		return this.fieldSource.hasAnnotation(name);
	}

	@Override
	public void setStringInitialize(String value) {
		this.fieldSource.setStringInitializer(value);
	}

	@Override
	public void setLiteralInitialize(String value) {
		this.fieldSource.setLiteralInitializer(value);
	}

	@Override
	public SortedSet<EAnnotation> getAnnotations() {
		// @formatter:off
		return this.fieldSource.getAnnotations()
				.stream()
				.map(EAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Adiciona uma nova annotation a classe
	 * 
	 * @param qualifiedName
	 * @return
	 */
	@Override
	public Optional<EAnnotation> addAnnotation(String qualifiedName) {
		if (StringUtils.isNotBlank(qualifiedName)) {
			if (!this.fieldSource.hasAnnotation(Types.toSimpleName(qualifiedName))) {
				AnnotationSource<JavaClassSource> annotationSource = this.fieldSource.addAnnotation();
				this.fieldSource.getOrigin().addImport(qualifiedName);
				annotationSource.setName(Types.toSimpleName(qualifiedName));
				return Optional.of(new EAnnotationImpl(annotationSource));
			}
		}
		return Optional.empty();
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
	public FieldSource<JavaClassSource> setName(String name) {
		return this.fieldSource.setName(name);
	}

	@Override
	public boolean isPrivate() {
		return this.fieldSource.isPrivate();
	}

	@Override
	public FieldSource<JavaClassSource> setFinal(boolean finl) {
		return this.fieldSource.setFinal(finl);
	}

	@Override
	public FieldSource<JavaClassSource> setStatic(boolean value) {
		return this.fieldSource.setStatic(value);
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
	public Visibility getVisibility() {
		return this.fieldSource.getVisibility();
	}

	@Override
	public JavaDocSource<FieldSource<JavaClassSource>> getJavaDoc() {
		return this.fieldSource.getJavaDoc();
	}

	@Override
	public FieldSource<JavaClassSource> setPackagePrivate() {
		return this.fieldSource.setPackagePrivate();
	}

	@Override
	public FieldSource<JavaClassSource> setPublic() {
		return this.fieldSource.setPublic();
	}

	@Override
	public boolean isTransient() {
		return this.fieldSource.isTransient();
	}

	@Override
	public FieldSource<JavaClassSource> setPrivate() {
		return this.fieldSource.setPrivate();
	}

	@Override
	public FieldSource<JavaClassSource> setType(Class<?> clazz) {
		return this.fieldSource.setType(clazz);
	}

	@Override
	public FieldSource<JavaClassSource> setProtected() {
		return this.fieldSource.setProtected();
	}

	@Override
	public FieldSource<JavaClassSource> setVisibility(Visibility scope) {
		return this.fieldSource.setVisibility(scope);
	}

	@Override
	public boolean isVolatile() {
		return this.fieldSource.isVolatile();
	}

	@Override
	public FieldSource<JavaClassSource> setType(String type) {
		return this.fieldSource.setType(type);
	}

	@Override
	public int getLineNumber() {
		return this.fieldSource.getLineNumber();
	}

	@Override
	public FieldSource<JavaClassSource> setType(JavaType<?> entity) {
		return this.fieldSource.setType(entity);
	}

	@Override
	public FieldSource<JavaClassSource> setLiteralInitializer(String value) {
		return this.fieldSource.setLiteralInitializer(value);
	}

	@Override
	public FieldSource<JavaClassSource> setStringInitializer(String value) {
		return this.fieldSource.setStringInitializer(value);
	}

	@Override
	public FieldSource<JavaClassSource> setTransient(boolean value) {
		return this.fieldSource.setTransient(value);
	}

	@Override
	public FieldSource<JavaClassSource> setVolatile(boolean value) {
		return this.fieldSource.setVolatile(value);
	}

	@Override
	public int compareTo(EField o) {
		return this.getName().compareTo(o.getName());
	}

}
