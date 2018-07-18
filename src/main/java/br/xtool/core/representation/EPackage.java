package br.xtool.core.representation;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */

public interface EPackage extends Comparable<EPackage> {

	String getName();

	String getDir();

}
