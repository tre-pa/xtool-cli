package br.xtool.core.representation.springboot;

import org.jboss.forge.roaster.model.JavaType;

public interface JavaTypeRepresentation<T extends JavaType<T>> extends JavaType<T> {

	/**
	 * 
	 * @return
	 */
	SpringBootProjectRepresentation getProject();

	/**
	 * Retorna o package da classe.
	 * 
	 * @return
	 */
	JavaPackageRepresentation getJavaPackage();
}
