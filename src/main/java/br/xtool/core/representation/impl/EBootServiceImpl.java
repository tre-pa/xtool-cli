package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.ServiceClassRepresentation;

public class EBootServiceImpl extends EJavaClassImpl implements ServiceClassRepresentation {

	public EBootServiceImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
