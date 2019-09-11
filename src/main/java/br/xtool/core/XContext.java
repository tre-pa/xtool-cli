package br.xtool.core;

/**
 * Contexto geral da aplicação.
 * 
 * @author jcruz
 *
 */
public interface XContext {

	/**
	 * Contexto do workspace.
	 * 
	 * @return
	 */
	WorkspaceContext getWorkspaceContext();

	/**
	 * Contexto do repositório.
	 * 
	 * @return
	 */
	RepositoryContext getRepositoryContext();

}
