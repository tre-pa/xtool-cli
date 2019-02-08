package br.xtool.core.implementation.representation;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.springboot.JpaProjectionRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class JpaProjectionRepresentationImpl extends JavaInterfaceRepresentationImpl implements JpaProjectionRepresentation {

	public JpaProjectionRepresentationImpl(SpringBootProjectRepresentation bootProject, JavaInterfaceSource javaInterfaceSource) {
		super(bootProject, javaInterfaceSource);
	}

}
