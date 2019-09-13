package br.xtool.implementation.representation;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.representation.springboot.SpringBootServiceClassRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;

public class SpringBootServiceClassRepresentationImpl extends JavaClassRepresentationImpl implements SpringBootServiceClassRepresentation {

	public SpringBootServiceClassRepresentationImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

}
