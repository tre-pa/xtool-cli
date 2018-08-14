package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootSpecification;

public class EBootSpecificationImpl extends EJavaClassImpl implements EBootSpecification {

	public EBootSpecificationImpl(EBootProject project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
