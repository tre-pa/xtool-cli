package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootService;

public class EBootServiceImpl extends EJavaClassImpl implements EBootService {

	public EBootServiceImpl(EBootProject project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
