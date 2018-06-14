package br.xtool.core.model;

import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class Entity implements Comparable<Entity> {

	private JavaClassSource javaClassSource;

	public Entity(JavaClassSource javaClassSource) {
		super();
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Nome da classe
	 * 
	 * @return
	 */
	public String getName() {
		return javaClassSource.getName();
	}

	@Override
	public int compareTo(Entity o) {
		return this.getName().compareTo(o.getName());
	}

}
