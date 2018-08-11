package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJavaMethodParameter;

public class EJavaMethodImpl implements EJavaMethod {

	private MethodSource<JavaClassSource> methodSource;

	private EJavaClass javaClass;

	public EJavaMethodImpl(EJavaClass javaClass, MethodSource<JavaClassSource> methodSource) {
		super();
		this.methodSource = methodSource;
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getJavaClass()
	 */
	@Override
	public EJavaClass getJavaClass() {
		return this.javaClass;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getReturnType()
	 */
	@Override
	public Type<JavaClassSource> getReturnType() {
		return this.methodSource.getReturnType();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getParameters()
	 */
	@Override
	public Collection<EJavaMethodParameter> getParameters() {
		// @formatter:off
		return this.methodSource.getParameters().stream()
				.map(parameterSource -> new EJavaMethodParameterImpl(this, parameterSource))
				.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getRoasterMethod()
	 */
	@Override
	public MethodSource<JavaClassSource> getRoasterMethod() {
		return this.methodSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getAnnotations()
	 */
	@Override
	public SortedSet<EJavaAnnotation> getAnnotations() {
		// @formatter:off
		return this.methodSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(EJavaMethod o) {
		return this.getName().compareTo(o.getName());
	}

}
