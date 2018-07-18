package br.xtool.core.representation;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */

public interface EJavaPackage extends Comparable<EJavaPackage> {

	String getName();

	String getDir();

}
