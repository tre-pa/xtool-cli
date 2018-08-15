package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.JavaDoc;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.SyntaxError;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EJavaInterface;
import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJavaPackage;

public class EJavaInterfaceImpl implements EJavaInterface {

	private JavaInterfaceSource javaInterfaceSource;

	public EJavaInterfaceImpl(JavaInterfaceSource javaInterfaceSource) {
		super();
		this.javaInterfaceSource = javaInterfaceSource;
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	@Override
	public EJavaPackage getJavaPackage() {
		return EJavaPackageImpl.of(this.javaInterfaceSource.getPackage());
	}

	@Override
	public String getCanonicalName() {
		return this.javaInterfaceSource.getCanonicalName();
	}

	@Override
	public String getQualifiedName() {
		return this.javaInterfaceSource.getQualifiedName();
	}

	@Override
	public List<SyntaxError> getSyntaxErrors() {
		return this.javaInterfaceSource.getSyntaxErrors();
	}

	@Override
	public boolean hasSyntaxErrors() {
		return this.javaInterfaceSource.hasSyntaxErrors();
	}

	@Override
	public boolean isClass() {
		return this.javaInterfaceSource.isClass();
	}

	@Override
	public boolean isEnum() {
		return this.javaInterfaceSource.isEnum();
	}

	@Override
	public boolean isInterface() {
		return this.javaInterfaceSource.isInterface();
	}

	@Override
	public boolean isAnnotation() {
		return this.javaInterfaceSource.isAnnotation();
	}

	@Override
	public JavaType<?> getEnclosingType() {
		return this.javaInterfaceSource.getEnclosingType();
	}

	@Override
	public String toUnformattedString() {
		return this.javaInterfaceSource.toUnformattedString();
	}

	@Override
	public String getPackage() {
		return this.javaInterfaceSource.getPackage();
	}

	@Override
	public boolean isDefaultPackage() {
		return this.javaInterfaceSource.isDefaultPackage();
	}

	@Override
	public String getName() {
		return this.javaInterfaceSource.getName();
	}

	@Override
	public boolean isPackagePrivate() {
		return this.javaInterfaceSource.isPackagePrivate();
	}

	@Override
	public boolean isPublic() {
		return this.javaInterfaceSource.isPublic();
	}

	@Override
	public boolean isPrivate() {
		return this.javaInterfaceSource.isPrivate();
	}

	@Override
	public boolean isProtected() {
		return this.javaInterfaceSource.isProtected();
	}

	@Override
	public Visibility getVisibility() {
		return this.javaInterfaceSource.getVisibility();
	}

	@Override
	public List<? extends Annotation<JavaInterfaceSource>> getAnnotations() {
		return this.javaInterfaceSource.getAnnotations();
	}

	@Override
	public boolean hasAnnotation(Class<? extends java.lang.annotation.Annotation> type) {
		return this.javaInterfaceSource.hasAnnotation(type);
	}

	@Override
	public boolean hasAnnotation(String type) {
		return this.javaInterfaceSource.hasAnnotation(type);
	}

	@Override
	public Annotation<JavaInterfaceSource> getAnnotation(Class<? extends java.lang.annotation.Annotation> type) {
		return this.getAnnotation(type);
	}

	@Override
	public Annotation<JavaInterfaceSource> getAnnotation(String type) {
		return this.javaInterfaceSource.getAnnotation(type);
	}

	@Override
	public Object getInternal() {
		return this.javaInterfaceSource.getInternal();
	}

	@Override
	public JavaInterfaceSource getOrigin() {
		return this.javaInterfaceSource.getOrigin();
	}

	@Override
	public JavaDoc<JavaInterfaceSource> getJavaDoc() {
		return this.javaInterfaceSource.getJavaDoc();
	}

	@Override
	public boolean hasJavaDoc() {
		return this.javaInterfaceSource.hasJavaDoc();
	}

	@Override
	public JavaInterfaceSource getRoasterInterface() {
		return this.javaInterfaceSource;
	}

	@Override
	public Collection<EJavaMethod<JavaInterfaceSource>> getJavaMethods() {
		// @formatter:off
		return this.javaInterfaceSource.getMethods().stream()
				.map(methodSource -> new EJavaMethodImpl<>(this.javaInterfaceSource, methodSource))
				.collect(Collectors.toList());
		// @formatter:on
	}

}