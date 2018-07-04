package br.xtool.core.representation;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import lombok.Getter;

public class EMethod implements Comparable<EMethod> {

	private MethodSource<JavaClassSource> methodSource;

	@Getter(lazy = true)
	private final SortedSet<EAnnotation> annotations = buildAnnotations();

	public EMethod(MethodSource<JavaClassSource> methodSource) {
		super();
		this.methodSource = methodSource;
	}

	/**
	 * Retorna o nome do método.
	 * 
	 * @return Nome do método.
	 */
	public String getName() {
		return this.methodSource.getName();
	}

	public boolean isStatic() {
		return methodSource.isStatic();
	}

	public boolean isConstructor() {
		return methodSource.isConstructor();
	}

	public boolean hasAnnotation(String type) {
		return methodSource.hasAnnotation(type);
	}

	public Type<JavaClassSource> getReturnType() {
		return methodSource.getReturnType();
	}

	public int getLineNumber() {
		return methodSource.getLineNumber();
	}

	public boolean isReturnTypeVoid() {
		return methodSource.isReturnTypeVoid();
	}

	public List<ParameterSource<JavaClassSource>> getParameters() {
		return methodSource.getParameters();
	}

	private SortedSet<EAnnotation> buildAnnotations() {
		// @formatter:off
		return this.methodSource.getAnnotations()
				.stream()
				.map(EAnnotation::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public int compareTo(EMethod o) {
		return this.getName().compareTo(o.getName());
	}

}
