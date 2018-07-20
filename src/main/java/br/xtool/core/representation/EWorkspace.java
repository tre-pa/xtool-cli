package br.xtool.core.representation;

import java.util.Collection;

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
	Collection<ESBootProject> getSpringBootProjects();

	/**
	 * Retorna a lista de projetos angular de aplicação.
	 * 
	 * @return
	 */
	Collection<ENgProject> getAngularProjections();

	/**
	 * Atualiza o workspace.
	 */
	void refresh();
}
