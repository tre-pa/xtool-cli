package br.xtool.core.representation;

import org.jboss.forge.roaster.model.JavaType;

public interface EJavaType<T extends JavaType<T>> extends JavaType<T> {

	/**
	 * 
	 * @return
	 */
	EBootProject getProject();

	/**
	 * Retorna o package da classe.
	 * 
	 * @return
	 */
	EJavaPackage getJavaPackage();
}
