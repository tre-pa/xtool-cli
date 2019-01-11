package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class EBootServiceImpl extends JavaClassRepresentationImpl implements ServiceClassRepresentation {

	public EBootServiceImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
