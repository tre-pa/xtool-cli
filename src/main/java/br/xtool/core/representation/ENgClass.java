package br.xtool.core.representation;

import java.nio.file.Path;

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

}
