package br.xtool.core.implementation.representation;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.springboot.SpringBootProjectionRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class SpringBootProjectionRepresentationImpl extends JavaInterfaceRepresentationImpl implements SpringBootProjectionRepresentation {

	public SpringBootProjectionRepresentationImpl(SpringBootProjectRepresentation bootProject, JavaInterfaceSource javaInterfaceSource) {
		super(bootProject, javaInterfaceSource);
	}

}
