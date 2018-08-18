package br.xtool.core.representation;

import java.nio.file.Path;

/**
 * Representação de pacote Java.
 * 
 * @author jcruz
 *
 */

public interface EJavaPackage extends Comparable<EJavaPackage> {

	String getName();

	String getDir();

	Path getPath();

	static String getDefaultPrefix() {
		return "br.jus.tre_pa";
	}

}
