package br.xtool.core.service;

import java.nio.file.Path;

import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EWorkspace;

/**
 * Classe de serviço do workspace.
 * 
 * @author jcruz
 *
 */
public interface WorkspaceService {

	/**
	 * Retorna o projeto atual de trabalho.
	 * 
	 * @return
	 */
	EProject getWorkingProject();

	/**
	 * Retorna a representação do workspace.
	 * 
	 * @return
	 */
	EWorkspace getWorkspace();

	/**
	 * Define o projeto do parametro como o projeto de trabalho atual.
	 * 
	 * @param project
	 */
	void setWorkingProject(EProject project);

	/**
	 * Retorna o projeto de trabalho atual.
	 * 
	 * @param projectClass
	 * @return
	 */
	<T extends EProject> T getWorkingProject(Class<T> projectClass);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Path createDirectory(String name);

}
