package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaProjection;

public class EJpaProjectionImpl extends EJavaInterfaceImpl implements EJpaProjection {

	public EJpaProjectionImpl(EBootProject bootProject, JavaInterfaceSource javaInterfaceSource) {
		super(bootProject, javaInterfaceSource);
	}

}
