package br.xtool.core;

import java.nio.file.Path;
import java.util.Map;

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

	/**
	 * Retorna o projeto de trabalho atual.
	 * 
	 * @param projectClass
	 * @return
	 */
	<T extends ProjectRepresentation> T getWorkingProject(Class<T> projectClass);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Path createDirectory(String name);

	/**
	 * 
	 * @param type
	 * @param name
	 * @param qualifier
	 * @param vars
	 * @return
	 */
	<T extends ProjectRepresentation> T createProject(ProjectRepresentation.Type type, String name, String qualifier, Map<String, Object> vars);

}
