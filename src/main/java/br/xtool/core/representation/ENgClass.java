package br.xtool.core.representation;

import java.io.File;

/**
 * Representação de um classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface ENgClass extends Comparable<ENgClass> {

	File getFile();

	String getName();

	String getFileName();

}
