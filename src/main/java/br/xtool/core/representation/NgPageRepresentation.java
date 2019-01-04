package br.xtool.core.representation;

import strman.Strman;

/**
 * Representação de um componente page de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface NgPageRepresentation extends NgComponentRepresentation {

	/**
	 * 
	 * @param name
	 * @return
	 */
	static String genFileName(String name) {
		return Strman.toKebabCase(name.concat("-page"));
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	static String genClassName(String name) {
		return Strman.toStudlyCase(name.concat("Page"));
	}
}
