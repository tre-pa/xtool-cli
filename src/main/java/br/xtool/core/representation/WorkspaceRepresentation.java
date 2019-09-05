package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.SortedSet;

import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootFullStackProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

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
	SortedSet<SpringBootFullStackProjectRepresentation> getSpringBootNgProjects();

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
