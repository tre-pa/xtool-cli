package br.xtool.core.representation.updater;

import org.jboss.forge.roaster.model.JavaClass;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public interface Updatable<S> {

	public S getSource();
}
