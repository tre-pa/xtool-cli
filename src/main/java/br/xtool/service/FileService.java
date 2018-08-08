package br.xtool.service;

import java.nio.file.Path;
import java.util.Map;

import br.xtool.core.representation.EProject;

/**
 * Classe que realiza operação no FileSystem.
 * 
 * @author jcruz
 *
 */
public interface FileService {

	/**
	 * Retorna uma lista de recursos do diretório src/main/resources/templates/**
	 * 
	 * @param resourcePath
	 * @param pathMatcher
	 * @param vars
	 * @return
	 */
	//	Collection<EResource> getTemplates(Path resourcePath, Map<String, Object> vars);

	/**
	 * 
	 * @param resourcePath
	 * @param vars
	 * @param destinationProject
	 */
	<T extends EProject> void copy(Path resourcePath, Map<String, Object> vars, T destinationProject);

	/**
	 * 
	 * @param resourcePath
	 * @param vars
	 * @param destinationPath
	 */
	void copy(Path resourcePath, Map<String, Object> vars, Path destinationPath);

}
