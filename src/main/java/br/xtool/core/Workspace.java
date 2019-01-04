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
	 * Cria um projeto no workspace.
	 * 
	 * @param projectClass
	 *            Classe do tipo de projeto.
	 * @param name
	 *            Nome do projeto.
	 * @param version
	 *            Versão do projeto.
	 * @return
	 */
	<T extends ProjectRepresentation> T createProject(Class<T> projectClass, ProjectRepresentation.Type type, String name, ProjectRepresentation.Version version, Map<String, Object> vars);
	
	/**
	 * 
	 * Cria um projeto no workspace.
	 * 
	 * @param projectClass
	 * @param type
	 * @param name
	 * @param version
	 * @param qualifier Qualificado de projeto
	 * @param vars
	 * @return
	 */
	<T extends ProjectRepresentation> T createProject(Class<T> projectClass, ProjectRepresentation.Type type, String name, ProjectRepresentation.Version version, String qualifier , Map<String, Object> vars);

}
