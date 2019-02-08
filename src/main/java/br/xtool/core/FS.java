package br.xtool.core;

import java.nio.file.Path;
import java.util.Map;

import br.xtool.core.representation.ProjectRepresentation;

/**
 * Serviços para operações em FileSystem.
 * 
 * @author jcruz
 *
 */
public interface FS {

	/**
	 * 
	 * @param resourcePath
	 * @param vars
	 * @param destinationProject
	 */
	<T extends ProjectRepresentation> void copy(Path resourcePath, Map<String, Object> vars, T destinationProject);

	/**
	 * 
	 * @param resourcePath
	 * @param vars
	 * @param destinationPath
	 */
	void copy(Path resourcePath, Map<String, Object> vars, Path destinationPath);

}
