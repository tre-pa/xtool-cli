package br.xtool.representation;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Representação do recurso.
 * 
 * @author jcruz
 *
 */
@Deprecated
public interface ResourceRepresentation {

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