package br.xtool.core.util;

import org.apache.commons.lang3.StringUtils;

import strman.Strman;

/**
 * 
 * Classe com os padrões de nomes.
 * 
 * @author jcruz
 *
 */
public class Names {

	//	/**
	//	 * 
	//	 * @param name
	//	 * @return
	//	 */
	//	public static String asDotCase(String name) {
	//		return StringUtils.join(StringUtils.split(Strman.toKebabCase(name), "-"), ".");
	//	}

	//	/**
	//	 * 
	//	 * @param name
	//	 * @return
	//	 */
	//	public static String asSpringBootBaseClass(String name) {
//		// @formatter:off
//		return Strman.toStudlyCase(
//						name.endsWith("Application") ? name.replace("Application", "") : name
//					);
//		// @formatter:on
	//	}

	//	/**
	//	 * 
	//	 * Retorna um nome válido de projeto Spring Boot.
	//	 * 
	//	 * @param name
	//	 * @return
	//	 */
	//	public static String asSpringBootProject(String name) {
//		// @formatter:off
//		return StringUtils.lowerCase(
//				Strman.toKebabCase(
//						StringUtils.endsWithIgnoreCase(name, "-service") ? 
//								name : 
//								name.concat("-service")));
//		// @formatter:on
	//	}

	/**
	 * Retorna um nome válido de classe JPA.
	 * 
	 * @param name
	 * @return
	 */
	public static String asEntityClass(String name) {
		return Strman.toStudlyCase(name);
	}

	/**
	 * Retorna um nome de classe repository válido.
	 * 
	 * @param name
	 * @return
	 */
	public static String asRepositoryClass(String name) {
		// @formatter:off
		return Strman.toStudlyCase(
				StringUtils.endsWithIgnoreCase(name,"Repository") ? 
						name : 
						name.concat("Repository"));
		// @formatter:on
	}

	/**
	 * Retorna um nome de classe service válido.
	 * 
	 * @param name
	 * @return
	 */
	public static String asServiceClass(String name) {
		// @formatter:off
		return Strman.toStudlyCase(
				name.endsWith("Service") ? 
					name : 
					name.concat("Service"));
		// @formatter:on
	}

	/**
	 * Retorna um nome válido de classe Rest.
	 * 
	 * @param name
	 * @return
	 */
	public static String asRestClass(String name) {
		// @formatter:off
		return Strman.toStudlyCase(
				name.endsWith("Rest") ? 
						name : 
						name.concat("Rest"));
		// @formatter:on
	}

	/**
	 * Retorna um nome válido de URL rest.
	 * 
	 * @param name
	 * @return
	 */
	public static String asRestPath(String name) {
		// @formatter:off
		return Strman.toKebabCase(
				name.endsWith("Rest") ? 
						name.replace("Rest", "") : 
						name);
		// @formatter:on
	}

}
