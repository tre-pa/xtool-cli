package br.xtool.core.representation.angular;

import java.io.File;

/**
 * Representa uma classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface ENgClass extends Comparable<ENgClass> {

	File getFile();

	String getName();

	String getFileName();

}
