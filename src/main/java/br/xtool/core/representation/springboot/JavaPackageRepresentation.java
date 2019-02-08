package br.xtool.core.representation.springboot;

import java.nio.file.Path;

/**
 * Representação de pacote Java.
 * 
 * @author jcruz
 *
 */

public interface JavaPackageRepresentation extends Comparable<JavaPackageRepresentation> {

	String getName();

	String getDir();

	Path getPath();

	@Deprecated
	static String getDefaultPrefix() {
		return "br.jus.tre_pa";
	}

}
