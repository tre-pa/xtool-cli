package br.xtool.core.representation;

/**
 * Representação de pacote Java.
 * 
 * @author jcruz
 *
 */

public interface EJavaPackage extends Comparable<EJavaPackage> {

	String getName();

	String getDir();

}