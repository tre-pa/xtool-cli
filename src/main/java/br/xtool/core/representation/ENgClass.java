package br.xtool.core.representation;

import java.nio.file.Path;

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

}
