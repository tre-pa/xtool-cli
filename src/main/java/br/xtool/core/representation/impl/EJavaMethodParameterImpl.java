package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJavaMethodParameter;

public class EJavaMethodParameterImpl implements EJavaMethodParameter {

	private ParameterSource<JavaClassSource> parameter;

	private EJavaMethod javaMethod;

	@Override
	public String getName() {
		return null;
	}

}
