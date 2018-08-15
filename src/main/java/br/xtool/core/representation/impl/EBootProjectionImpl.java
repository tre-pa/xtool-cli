package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProjection;

public class EBootProjectionImpl extends EJavaInterfaceImpl implements EBootProjection {

	public EBootProjectionImpl(EBootProject bootProject, JavaInterfaceSource javaInterfaceSource) {
		super(bootProject, javaInterfaceSource);
	}

}
