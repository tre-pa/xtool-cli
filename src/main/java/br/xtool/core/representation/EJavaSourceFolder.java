package br.xtool.core.representation;

import java.util.SortedSet;

/**
 * Representação de um source folder Java.
 * 
 * @author jcruz
 *
 */
public interface EJavaSourceFolder {

	/**
	 * Retorna o caminho do source folder java.
	 * 
	 * @return
	 */
	String getPath();

	/**
	 * Retorna a lista de pacotes do source folder java.
	 * 
	 * @return
	 */
	SortedSet<EJavaPackage> getPackages();

}
