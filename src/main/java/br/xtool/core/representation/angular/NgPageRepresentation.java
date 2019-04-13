package br.xtool.core.representation.angular;

import java.util.List;

import strman.Strman;

/**
 * Representação de um componente page de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface NgPageRepresentation extends NgComponentRepresentation {
	
	public static final String NAVIGATION_PATTERN = "\\s*navigation\\s*=\\s*";
	
	List<NgPageNavigationRepresentation> getNavigations();

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
