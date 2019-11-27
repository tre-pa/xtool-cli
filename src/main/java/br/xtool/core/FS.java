package br.xtool.core;

import br.xtool.representation.ProjectRepresentation;

import java.nio.file.Path;
import java.util.Map;

/**
 * Serviços para operações em FileSystem.
 * 
 * @author jcruz
 *
 */
@Deprecated
public interface FS {

	/**
	 * 
	 * @param resourcePath
	 * @param vars
	 * @param destinationProject
	 */
	@Deprecated
	<T extends ProjectRepresentation> void copy(Path resourcePath, Map<String, Object> vars, T destinationProject);

	/**
	 * 
	 * @param resourcePath
	 * @param vars
	 * @param destinationPath
	 */
	void copy(Path resourcePath, Map<String, Object> vars, Path destinationPath);

}
