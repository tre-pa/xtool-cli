package br.xtool.core.representation;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Representação do recurso.
 * 
 * @author jcruz
 *
 */
public interface EResource {

	/**
	 * Path raiz dos recursos.
	 */
	static Path ROOT_PATH = Paths.get("src/main/resources/templates");

	/**
	 * Path do recurso.
	 * 
	 * @return
	 */
	Path getRelativePath();

	/**
	 * Conteúdo em byte do recurso.
	 * 
	 * @return
	 */
	byte[] read();

}
