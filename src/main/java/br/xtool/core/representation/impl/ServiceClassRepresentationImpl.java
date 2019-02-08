package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class ServiceClassRepresentationImpl extends JavaClassRepresentationImpl implements ServiceClassRepresentation {

	public ServiceClassRepresentationImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
