package br.xtool.core.model;

import org.jboss.forge.roaster.model.source.JavaClassSource;

public class Entity {

	private JavaClassSource javaClassSource;

	public Entity(JavaClassSource javaClassSource) {
		super();
		this.javaClassSource = javaClassSource;
	}
	
	public String getName() {
		return javaClassSource.getName();
	}

}
