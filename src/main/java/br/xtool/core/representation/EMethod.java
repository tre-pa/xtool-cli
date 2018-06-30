package br.xtool.core.representation;

import java.util.List;

import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

public class EMethod implements Comparable<EMethod> {

	private ESpringBootProject springBootProject;

	private JavaClassSource owner;

	private MethodSource<JavaClassSource> methodSource;

	public EMethod(ESpringBootProject springBootProject, JavaClassSource owner, MethodSource<JavaClassSource> methodSource) {
		super();
		this.springBootProject = springBootProject;
		this.owner = owner;
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

	public List<? extends Annotation<JavaClassSource>> getAnnotations() {
		return methodSource.getAnnotations();
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

	@Override
	public int compareTo(EMethod o) {
		return this.getName().compareTo(o.getName());
	}

}
