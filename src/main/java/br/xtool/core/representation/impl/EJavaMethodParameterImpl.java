package br.xtool.core.representation.impl;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJavaMethodParameter;

public class EJavaMethodParameterImpl<T extends JavaSource<T>> implements EJavaMethodParameter<T> {

	private EJavaMethod<T> javaMethod;

	private ParameterSource<T> parameterSource;

	public EJavaMethodParameterImpl(EJavaMethod<T> javaMethod, ParameterSource<T> parameterSource) {
		super();
		this.javaMethod = javaMethod;
		this.parameterSource = parameterSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethodParameter#getName()
	 */
	@Override
	public String getName() {
		return this.parameterSource.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethodParameter#getJavaMethod()
	 */
	@Override
	public EJavaMethod<T> getJavaMethod() {
		return this.javaMethod;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethodParameter#getType()
	 */
	@Override
	public Type<T> getType() {
		return this.getType();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethodParameter#getAnnotations()
	 */
	@Override
	public SortedSet<EJavaAnnotation<T>> getAnnotations() {
		// @formatter:off
		return this.parameterSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethodParameter#getRoasterParameter()
	 */
	@Override
	public ParameterSource<T> getRoasterParameter() {
		return this.parameterSource;
	}

}