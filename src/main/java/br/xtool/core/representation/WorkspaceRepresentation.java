package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.SortedSet;

import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Representação do workspace.
 * 
 * @author jcruz
 *
 */
public interface WorkspaceRepresentation {

	Path getPath();

	/**
	 * Retorna a lista de projetos spring boot do workspace.
	 * 
	 * @return
	 */
	SortedSet<SpringBootProjectRepresentation> getSpringBootProjects();

	/**
	 * Retorna a lista de projetos angular de aplicação.
	 * 
	 * @return
	 */
	SortedSet<NgProjectRepresentation> getAngularProjections();

	/**
	 * 
	 * @return
	 */
	SortedSet<ProjectRepresentation> getProjects();

	/**
	 * Atualiza o workspace.
	 */
	void refresh();
}
