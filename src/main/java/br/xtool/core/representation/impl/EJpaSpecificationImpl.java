package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaSpecification;

public class EJpaSpecificationImpl extends EJavaClassImpl implements EJpaSpecification {

	public EJpaSpecificationImpl(EBootProject project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
