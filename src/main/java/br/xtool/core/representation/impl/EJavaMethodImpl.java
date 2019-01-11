package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import br.xtool.core.representation.springboot.JavaAnnotationRepresentation;
import br.xtool.core.representation.springboot.JavaMethodParameterRepresentation;
import br.xtool.core.representation.springboot.JavaMethodRepresentation;

public class EJavaMethodImpl<T extends JavaSource<T>> implements JavaMethodRepresentation<T> {

	private MethodSource<T> methodSource;

	private T javaSource;

	public EJavaMethodImpl(T javaSource, MethodSource<T> methodSource) {
		super();
		this.methodSource = methodSource;
		this.javaSource = javaSource;
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
	public JavaSource<T> getJavaSource() {
		return this.javaSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getReturnType()
	 */
	@Override
	public Type<T> getReturnType() {
		return this.methodSource.getReturnType();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getParameters()
	 */
	@Override
	public Collection<JavaMethodParameterRepresentation<T>> getParameters() {
		// @formatter:off
		return this.methodSource.getParameters().stream()
				.map(parameterSource -> new EJavaMethodParameterImpl<T>(this, parameterSource))
				.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getRoasterMethod()
	 */
	@Override
	public MethodSource<T> getRoasterMethod() {
		return this.methodSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaMethod#getAnnotations()
	 */
	@Override
	public SortedSet<JavaAnnotationRepresentation<T>> getAnnotations() {
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
	public int compareTo(JavaMethodRepresentation<T> o) {
		return this.getName().compareTo(o.getName());
	}

}
