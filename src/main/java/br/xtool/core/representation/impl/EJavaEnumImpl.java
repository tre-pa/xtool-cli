package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.JavaDoc;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.SyntaxError;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.JavaEnumSource;

import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.representation.springboot.JavaPackageRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class EJavaEnumImpl implements JavaEnumRepresentation {

	private SpringBootProjectRepresentation project;

	private JavaEnumSource javaEnumSource;

	public EJavaEnumImpl(SpringBootProjectRepresentation project, JavaEnumSource javaEnumSource) {
		super();
		this.project = project;
		this.javaEnumSource = javaEnumSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaType#getProject()
	 */
	@Override
	public SpringBootProjectRepresentation getProject() {
		return this.project;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaType#getJavaPackage()
	 */
	@Override
	public JavaPackageRepresentation getJavaPackage() {
		return EJavaPackageImpl.of(this.javaEnumSource.getPackage());
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#getCanonicalName()
	 */
	@Override
	public String getCanonicalName() {
		return this.javaEnumSource.getCanonicalName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#getQualifiedName()
	 */
	@Override
	public String getQualifiedName() {
		return this.javaEnumSource.getQualifiedName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#getSyntaxErrors()
	 */
	@Override
	public List<SyntaxError> getSyntaxErrors() {
		return this.javaEnumSource.getSyntaxErrors();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#hasSyntaxErrors()
	 */
	@Override
	public boolean hasSyntaxErrors() {
		return this.javaEnumSource.hasSyntaxErrors();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#isClass()
	 */
	@Override
	public boolean isClass() {
		return this.javaEnumSource.isClass();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#isEnum()
	 */
	@Override
	public boolean isEnum() {
		return this.javaEnumSource.isEnum();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#isInterface()
	 */
	@Override
	public boolean isInterface() {
		return this.javaEnumSource.isInterface();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#isAnnotation()
	 */
	@Override
	public boolean isAnnotation() {
		return this.javaEnumSource.isAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#getEnclosingType()
	 */
	@Override
	public JavaType<?> getEnclosingType() {
		return this.javaEnumSource.getEnclosingType();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaType#toUnformattedString()
	 */
	@Override
	public String toUnformattedString() {
		return this.javaEnumSource.toUnformattedString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.Packaged#getPackage()
	 */
	@Override
	public String getPackage() {
		return this.javaEnumSource.getPackage();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.Packaged#isDefaultPackage()
	 */
	@Override
	public boolean isDefaultPackage() {
		return this.javaEnumSource.isDefaultPackage();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.Named#getName()
	 */
	@Override
	public String getName() {
		return this.javaEnumSource.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.VisibilityScoped#isPackagePrivate()
	 */
	@Override
	public boolean isPackagePrivate() {
		return this.javaEnumSource.isPackagePrivate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.VisibilityScoped#isPublic()
	 */
	@Override
	public boolean isPublic() {
		return this.javaEnumSource.isPublic();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.VisibilityScoped#isPrivate()
	 */
	@Override
	public boolean isPrivate() {
		return this.javaEnumSource.isPrivate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.VisibilityScoped#isProtected()
	 */
	@Override
	public boolean isProtected() {
		return this.javaEnumSource.isProtected();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.VisibilityScoped#getVisibility()
	 */
	@Override
	public Visibility getVisibility() {
		return this.javaEnumSource.getVisibility();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.AnnotationTarget#getAnnotations()
	 */
	@Override
	public List<? extends Annotation<JavaEnumSource>> getAnnotations() {
		return this.javaEnumSource.getAnnotations();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.AnnotationTarget#hasAnnotation(java.lang.Class)
	 */
	@Override
	public boolean hasAnnotation(Class<? extends java.lang.annotation.Annotation> type) {
		return this.javaEnumSource.hasAnnotation(type);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.AnnotationTarget#hasAnnotation(java.lang.String)
	 */
	@Override
	public boolean hasAnnotation(String type) {
		return this.javaEnumSource.hasAnnotation(type);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.AnnotationTarget#getAnnotation(java.lang.Class)
	 */
	@Override
	public Annotation<JavaEnumSource> getAnnotation(Class<? extends java.lang.annotation.Annotation> type) {
		return this.javaEnumSource.getAnnotation(type);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.AnnotationTarget#getAnnotation(java.lang.String)
	 */
	@Override
	public Annotation<JavaEnumSource> getAnnotation(String type) {
		return this.javaEnumSource.getAnnotation(type);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.Internal#getInternal()
	 */
	@Override
	public Object getInternal() {
		return this.javaEnumSource.getInternal();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.Origin#getOrigin()
	 */
	@Override
	public JavaEnumSource getOrigin() {
		return this.javaEnumSource.getOrigin();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaDocCapable#getJavaDoc()
	 */
	@Override
	public JavaDoc<JavaEnumSource> getJavaDoc() {
		return this.javaEnumSource.getJavaDoc();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.forge.roaster.model.JavaDocCapable#hasJavaDoc()
	 */
	@Override
	public boolean hasJavaDoc() {
		return this.javaEnumSource.hasJavaDoc();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaEnum#getConstants()
	 */
	@Override
	public Collection<String> getConstants() {
		// @formatter:off
		return this.javaEnumSource.getEnumConstants()
				.stream()
				.map(enumConstant -> enumConstant.getName())
				.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaEnum#getRoasterEnum()
	 */
	@Override
	public JavaEnumSource getRoasterEnum() {
		return this.javaEnumSource;
	}

}
