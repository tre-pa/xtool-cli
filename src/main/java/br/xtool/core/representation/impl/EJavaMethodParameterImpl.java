package br.xtool.core.representation.impl;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJavaMethodParameter;

public class EJavaMethodParameterImpl implements EJavaMethodParameter {

	private EJavaMethod javaMethod;

	private ParameterSource<JavaClassSource> parameterSource;

	public EJavaMethodParameterImpl(EJavaMethod javaMethod, ParameterSource<JavaClassSource> parameterSource) {
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
	public EJavaMethod getJavaMethod() {
		return this.javaMethod;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethodParameter#getType()
	 */
	@Override
	public Type<JavaClassSource> getType() {
		return this.getType();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethodParameter#getAnnotations()
	 */
	@Override
	public SortedSet<EJavaAnnotation> getAnnotations() {
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
	public ParameterSource<JavaClassSource> getRoasterParameter() {
		return this.parameterSource;
	}

}
