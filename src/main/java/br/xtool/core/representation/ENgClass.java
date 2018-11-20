package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import strman.Strman;

/**
 * Representação de um classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface ENgClass extends Comparable<ENgClass> {

	Path getPath();

	String getName();

	String getFileName();

	/**
	 * 
	 * @param name
	 * @return
	 */
	static String genFileName(String name) {
		return Strman.toKebabCase(name);
	}

	static Map<String, String> typesMap() {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("Long", "number");
				put("Integer", "number");
				put("Short", "number");
				put("BigDecimal", "number");
				put("Boolean", "boolean");
				put("String", "string");
				put("Date", "Date");
				put("LocalDate", "Date");
				put("LocalDateTime", "Date");
			}
		};
	}

}
