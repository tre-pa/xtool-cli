package br.xtool.core.representation.impl;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaMethod;

public class EJavaMethodImpl implements EJavaMethod {

	private MethodSource<JavaClassSource> methodSource;

	private EJavaClass javaClass;

	private EBootProject bootProject;

	public EJavaMethodImpl(EBootProject bootProject, EJavaClass javaClass, MethodSource<JavaClassSource> methodSource) {
		super();
		this.methodSource = methodSource;
		this.bootProject = bootProject;
		this.javaClass = javaClass;
	}

	/**
	 * Retorna o nome do método.
	 * 
	 * @return Nome do método.
	 */
	@Override
	public String getName() {
		return this.methodSource.getName();
	}

	@Override
	public boolean isStatic() {
		return this.methodSource.isStatic();
	}

	@Override
	public boolean isConstructor() {
		return this.methodSource.isConstructor();
	}

	@Override
	public boolean hasAnnotation(String type) {
		return this.methodSource.hasAnnotation(type);
	}

	@Override
	public Type<JavaClassSource> getReturnType() {
		return this.methodSource.getReturnType();
	}

	@Override
	public int getLineNumber() {
		return this.methodSource.getLineNumber();
	}

	@Override
	public boolean isReturnTypeVoid() {
		return this.methodSource.isReturnTypeVoid();
	}

	@Override
	public List<ParameterSource<JavaClassSource>> getParameters() {
		return this.methodSource.getParameters();
	}

	@Override
	public MethodSource<JavaClassSource> getRoasterMethod() {
		return this.methodSource;
	}

	@Override
	public SortedSet<EJavaAnnotation> getAnnotations() {
		// @formatter:off
		return this.methodSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public int compareTo(EJavaMethod o) {
		return this.getName().compareTo(o.getName());
	}

}
