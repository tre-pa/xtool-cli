package br.xtool.context;

import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.WorkspaceRepresentation;

/**
 * Serviços do workspace.
 * 
 * @author jcruz
 *
 */
public interface WorkspaceContext {

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
