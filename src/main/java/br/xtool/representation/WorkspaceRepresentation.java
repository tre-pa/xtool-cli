package br.xtool.representation;

import java.nio.file.Path;
import java.util.SortedSet;

import br.xtool.representation.angular.NgProjectRepresentation;
import br.xtool.representation.springboot.SpringBootFullStackProjectRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;

/**
 * Representação do workspace.
 * 
 * @author jcruz
 *
 */
public interface WorkspaceRepresentation {

	/**
	 * Retorna o caminho do workspace.
	 * 
	 * @return Caminho do Workspace.
	 */
	Path getPath();

	/**
	 * Retorna a lista de projetos spring boot do workspace.
	 * 
	 * @return {@link SpringBootProjectRepresentation}
	 */
	SortedSet<SpringBootProjectRepresentation> getSpringBootProjects();

	/**
	 * Retorna a lista de projetos angular de aplicação.
	 * 
	 * @return {@link NgProjectRepresentation}
	 */
	SortedSet<NgProjectRepresentation> getAngularProjects();

	/**
	 * Retorna a lista de projetos multi-módulo SpringBoot e Angular.
	 * 
	 * @return {@link SpringBootFullStackProjectRepresentation}
	 */
	SortedSet<SpringBootFullStackProjectRepresentation> getSpringBootFullStackProjects();

	/**
	 * Retorna a lista de todos os projetos do workspace.
	 * 
	 * @return {@link ProjectRepresentation}
	 */
	SortedSet<? extends ProjectRepresentation> getProjects();

	/**
	 * Atualiza o workspace.
	 */
	void refresh();
}
