package br.xtool.core.service;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EResource;

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
	Collection<EResource> getTemplates(Path resourcePath, Map<String, Object> vars);

	/**
	 * 
	 * @param resources
	 * @param destinationProject
	 */
	<T extends EProject> void copy(Collection<EResource> resources, T destinationProject);

	/**
	 * Copia a lista de recursos para o path de destino.
	 * 
	 * @param resources
	 * @param destinationPath
	 */
	void copy(Collection<EResource> resources, Path path);

}
