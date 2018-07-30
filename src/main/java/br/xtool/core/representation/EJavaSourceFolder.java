package br.xtool.core.representation;

import java.util.SortedSet;

/**
 * Representação de um source folder Java.
 * 
 * @author jcruz
 *
 */
public interface EJavaSourceFolder extends ESourceFolder {

	/**
	 * Retorna a lista de pacotes do source folder java.
	 * 
	 * @return
	 */
	SortedSet<EJavaPackage> getPackages();

	/**
	 * Retorna o projeto Spring Boot associado.
	 * 
	 * @return
	 */
	EBootProject getBootProject();

}
