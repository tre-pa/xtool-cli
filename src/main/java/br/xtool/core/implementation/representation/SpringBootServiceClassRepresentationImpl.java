package br.xtool.core.implementation.representation;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.springboot.SpringBootServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class SpringBootServiceClassRepresentationImpl extends JavaClassRepresentationImpl implements SpringBootServiceClassRepresentation {

	public SpringBootServiceClassRepresentationImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
