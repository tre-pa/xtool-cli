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
import org.jboss.forge.roaster.model.util.Types;

public class EField implements Comparable<EAttribute> {

	protected FieldSource<JavaClassSource> fieldSource;

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
		return Stream.of("List", "Set", "Collection").peek(type -> this.getType().getName()).anyMatch(type -> type.equals(this.getType().getName()));
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

	public SortedSet<EAnnotation> getAnnotations() {
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
			if (!this.fieldSource.hasAnnotation(Types.toSimpleName(qualifiedName))) {
				AnnotationSource<JavaClassSource> annotationSource = this.fieldSource.addAnnotation();
				this.fieldSource.getOrigin().addImport(qualifiedName);
				annotationSource.setName(Types.toSimpleName(qualifiedName));
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
		return this.fieldSource.isFinal();
	}

	public boolean isPackagePrivate() {
		return this.fieldSource.isPackagePrivate();
	}

	public boolean isPublic() {
		return this.fieldSource.isPublic();
	}

	public FieldSource<JavaClassSource> setName(String name) {
		return this.fieldSource.setName(name);
	}

	public boolean isPrivate() {
		return this.fieldSource.isPrivate();
	}

	public FieldSource<JavaClassSource> setFinal(boolean finl) {
		return this.fieldSource.setFinal(finl);
	}

	public FieldSource<JavaClassSource> setStatic(boolean value) {
		return this.fieldSource.setStatic(value);
	}

	public boolean isProtected() {
		return this.fieldSource.isProtected();
	}

	public boolean hasJavaDoc() {
		return this.fieldSource.hasJavaDoc();
	}

	public Visibility getVisibility() {
		return this.fieldSource.getVisibility();
	}

	public JavaDocSource<FieldSource<JavaClassSource>> getJavaDoc() {
		return this.fieldSource.getJavaDoc();
	}

	public FieldSource<JavaClassSource> setPackagePrivate() {
		return this.fieldSource.setPackagePrivate();
	}

	public FieldSource<JavaClassSource> setPublic() {
		return this.fieldSource.setPublic();
	}

	public boolean isTransient() {
		return this.fieldSource.isTransient();
	}

	public FieldSource<JavaClassSource> setPrivate() {
		return this.fieldSource.setPrivate();
	}

	public FieldSource<JavaClassSource> setType(Class<?> clazz) {
		return this.fieldSource.setType(clazz);
	}

	public FieldSource<JavaClassSource> setProtected() {
		return this.fieldSource.setProtected();
	}

	public FieldSource<JavaClassSource> setVisibility(Visibility scope) {
		return this.fieldSource.setVisibility(scope);
	}

	public boolean isVolatile() {
		return this.fieldSource.isVolatile();
	}

	public FieldSource<JavaClassSource> setType(String type) {
		return this.fieldSource.setType(type);
	}

	public int getLineNumber() {
		return this.fieldSource.getLineNumber();
	}

	public FieldSource<JavaClassSource> setType(JavaType<?> entity) {
		return this.fieldSource.setType(entity);
	}

	public FieldSource<JavaClassSource> setLiteralInitializer(String value) {
		return this.fieldSource.setLiteralInitializer(value);
	}

	public FieldSource<JavaClassSource> setStringInitializer(String value) {
		return this.fieldSource.setStringInitializer(value);
	}

	public FieldSource<JavaClassSource> setTransient(boolean value) {
		return this.fieldSource.setTransient(value);
	}

	public FieldSource<JavaClassSource> setVolatile(boolean value) {
		return this.fieldSource.setVolatile(value);
	}

}
