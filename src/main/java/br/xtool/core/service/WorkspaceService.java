package br.xtool.core.service;

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
	 * 
	 * @return
	 */
	EWorkspace getWorkspace();

	void setWorkingProject(EProject project);

}
