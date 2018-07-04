package br.xtool.core.representation;

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

import lombok.Getter;

public class EField implements Comparable<EAttribute> {

	protected FieldSource<JavaClassSource> fieldSource;

	@Getter(lazy = true)
	private final SortedSet<EAnnotation> annotations = buildAnnotations();

	public EField(FieldSource<JavaClassSource> fieldSource) {
		super();
		this.fieldSource = fieldSource;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	public String getName() {
		return this.fieldSource.getName();
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}

	/**
	 * Verifica se o atributo é uma coleção
	 * 
	 * @return
	 */
	public boolean isCollection() {
		return Stream.of("List", "Set", "Collection").anyMatch(type -> type.equals(this.getType().getName()));
	}

	/**
	 * Verifica se o atributo é static.
	 * 
	 * @return
	 */
	public boolean isStatic() {
		return this.fieldSource.isStatic();
	}

	public boolean hasAnnotation(String name) {
		return this.fieldSource.hasAnnotation(name);
	}

	public void setStringInitialize(String value) {
		this.fieldSource.setStringInitializer(value);
	}

	public void setLiteralInitialize(String value) {
		this.fieldSource.setLiteralInitializer(value);
	}

	private SortedSet<EAnnotation> buildAnnotations() {
		// @formatter:off
		return this.fieldSource.getAnnotations()
				.stream()
				.map(EAnnotation::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Adiciona uma nova annotation a classe
	 * 
	 * @param qualifiedName
	 * @return
	 */
	public Optional<EAnnotation> addAnnotation(String qualifiedName) {
		if (StringUtils.isNotBlank(qualifiedName)) {
			String[] annotationTokens = StringUtils.split(qualifiedName, ".");
			String annotationName = annotationTokens[annotationTokens.length - 1];
			if (!fieldSource.hasAnnotation(annotationName)) {
				AnnotationSource<JavaClassSource> annotationSource = this.fieldSource.addAnnotation();
				this.fieldSource.getOrigin().addImport(qualifiedName);
				annotationSource.setName(annotationName);
				return Optional.of(new EAnnotation(annotationSource));
			}
		}
		return Optional.empty();
	}

	@Override
	public int compareTo(EAttribute o) {
		return this.getName().compareTo(o.getName());
	}

	public boolean isFinal() {
		return fieldSource.isFinal();
	}

	public boolean isPackagePrivate() {
		return fieldSource.isPackagePrivate();
	}

	public boolean isPublic() {
		return fieldSource.isPublic();
	}

	public FieldSource<JavaClassSource> setName(String name) {
		return fieldSource.setName(name);
	}

	public boolean isPrivate() {
		return fieldSource.isPrivate();
	}

	public FieldSource<JavaClassSource> setFinal(boolean finl) {
		return fieldSource.setFinal(finl);
	}

	public FieldSource<JavaClassSource> setStatic(boolean value) {
		return fieldSource.setStatic(value);
	}

	public boolean isProtected() {
		return fieldSource.isProtected();
	}

	public boolean hasJavaDoc() {
		return fieldSource.hasJavaDoc();
	}

	public Visibility getVisibility() {
		return fieldSource.getVisibility();
	}

	public JavaDocSource<FieldSource<JavaClassSource>> getJavaDoc() {
		return fieldSource.getJavaDoc();
	}

	public FieldSource<JavaClassSource> setPackagePrivate() {
		return fieldSource.setPackagePrivate();
	}

	public FieldSource<JavaClassSource> setPublic() {
		return fieldSource.setPublic();
	}

	public boolean isTransient() {
		return fieldSource.isTransient();
	}

	public FieldSource<JavaClassSource> setPrivate() {
		return fieldSource.setPrivate();
	}

	public FieldSource<JavaClassSource> setType(Class<?> clazz) {
		return fieldSource.setType(clazz);
	}

	public FieldSource<JavaClassSource> setProtected() {
		return fieldSource.setProtected();
	}

	public FieldSource<JavaClassSource> setVisibility(Visibility scope) {
		return fieldSource.setVisibility(scope);
	}

	public boolean isVolatile() {
		return fieldSource.isVolatile();
	}

	public FieldSource<JavaClassSource> setType(String type) {
		return fieldSource.setType(type);
	}

	public int getLineNumber() {
		return fieldSource.getLineNumber();
	}

	public FieldSource<JavaClassSource> setType(JavaType<?> entity) {
		return fieldSource.setType(entity);
	}

	public FieldSource<JavaClassSource> setLiteralInitializer(String value) {
		return fieldSource.setLiteralInitializer(value);
	}

	public FieldSource<JavaClassSource> setStringInitializer(String value) {
		return fieldSource.setStringInitializer(value);
	}

	public FieldSource<JavaClassSource> setTransient(boolean value) {
		return fieldSource.setTransient(value);
	}

	public FieldSource<JavaClassSource> setVolatile(boolean value) {
		return fieldSource.setVolatile(value);
	}

}
