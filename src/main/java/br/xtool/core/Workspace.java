package br.xtool.core;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.WorkspaceRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Serviços do workspace.
 * 
 * @author jcruz
 *
 */
public interface Workspace {

	/**
	 * Retorna o tipo do projeto de trabalho.
	 * 
	 * @return
	 */
	ProjectRepresentation.Type getWorkingProjectType();

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
	<T extends ProjectRepresentation> T createProject(ProjectRepresentation.Type type, String version, String name, Map<String, Object> vars);

	/**
	 * Retorna true se o working project é Spring Boot.
	 * 
	 * @return
	 */
	boolean isSpringBootProject();

	/**
	 * Retorna true se o working project é Angular.
	 * 
	 * @return
	 */
	boolean isAngularProject();

	/**
	 * Retorna true se o working project é Spring Boot e Angular.
	 * 
	 * @return
	 */
	boolean isSpringBootNgProject();

	/**
	 * Retorna o projeto Spring Boot do SpringBootProjectRepresentation ou SpringBootNgProjectRepresentation
	 * 
	 * @return
	 */
	Optional<SpringBootProjectRepresentation> getSpringBootProject();

	/**
	 * Retorna o projeto Angular do NgProjectRepresentation ou SpringBootNgProjectRepresentation
	 * 
	 * @return
	 */
	Optional<NgProjectRepresentation> getAngularProject();

}
