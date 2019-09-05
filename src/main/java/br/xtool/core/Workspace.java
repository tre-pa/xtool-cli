package br.xtool.core;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.WorkspaceRepresentation;

/**
 * Serviços do workspace.
 * 
 * @author jcruz
 *
 */
public interface Workspace {

	/**
	 * Retorna o projeto atual de trabalho.
	 * 
	 * @return
	 */
	ProjectRepresentation getWorkingProject();

	/**
	 * Retorna a representação do workspace.
	 * 
	 * @return
	 */
	WorkspaceRepresentation getWorkspace();

	/**
	 * Define o projeto do parametro como o projeto de trabalho atual.
	 * 
	 * @param project
	 */
	void setWorkingProject(ProjectRepresentation project);

}
