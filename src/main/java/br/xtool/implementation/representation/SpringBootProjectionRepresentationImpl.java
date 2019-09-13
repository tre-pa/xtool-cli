package br.xtool.implementation.representation;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.representation.springboot.SpringBootProjectionRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;

public class SpringBootProjectionRepresentationImpl extends JavaInterfaceRepresentationImpl implements SpringBootProjectionRepresentation {

	public SpringBootProjectionRepresentationImpl(SpringBootProjectRepresentation bootProject, JavaInterfaceSource javaInterfaceSource) {
		super(bootProject, javaInterfaceSource);
	}

}
