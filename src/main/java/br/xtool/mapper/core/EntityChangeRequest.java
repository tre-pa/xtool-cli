package br.xtool.mapper.core;

import org.jboss.forge.roaster.model.source.JavaClassSource;

public interface EntityChangeRequest {

	public boolean test(JavaClassSource javaClassSource);

	public void apply(JavaClassSource javaClassSource);
}
