package br.xtool.core.representation;

import java.util.SortedSet;

/**
 * Representão do workspace.
 * 
 * @author jcruz
 *
 */
public interface EWorkspace {

	/**
	 * Retorna a lista de projetos spring boot do workspace.
	 * 
	 * @return
	 */
	SortedSet<ESBootProject> getSpringBootProjects();

	/**
	 * Retorna a lista de projetos angular de aplicação.
	 * 
	 * @return
	 */
	SortedSet<ENgProject> getAngularProjections();

	/**
	 * Atualiza o workspace.
	 */
	void refresh();
}
