package br.xtool.core.representation;

import strman.Strman;

/**
 * Representação de um componente page de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface ENgPage extends ENgComponent {

	/**
	 * 
	 * @param name
	 * @return
	 */
	static String genFileName(String name) {
		return Strman.toKebabCase(name);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	static String genClassName(String name) {
		return Strman.toStudlyCase(name);
	}
}
