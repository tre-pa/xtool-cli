package br.xtool.core.representation.angular;

import java.util.HashMap;
import java.util.Map;

import strman.Strman;

/**
 * Representação de um classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface NgClassRepresentation extends NgTypeRepresentation, Comparable<NgClassRepresentation> {

	String getName();

	/**
	 * 
	 * @param name
	 * @return
	 */
	static String genFileName(String name) {
		return Strman.toKebabCase(name);
	}

	static Map<String, String> typescriptTypeMap() {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("Long", "number");
				put("Integer", "number");
				put("Short", "number");
				put("BigDecimal", "number");
				put("BigInteger", "number");
				put("Boolean", "boolean");
				put("String", "string");
				put("Date", "Date");
				put("LocalDate", "Date");
				put("LocalDateTime", "Date");
			}
		};
	}

}
