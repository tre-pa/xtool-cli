package br.xtool.core;

import java.util.Set;

import br.xtool.representation.repo.RepositoryRepresentation;

/**
 * Contexto do repositório xtool.
 * 
 * @author jcruz
 *
 */
public interface RepositoryContext {

	/**
	 * Retorna a lista de repositórios de componentes.
	 * 
	 * @return
	 */
	Set<RepositoryRepresentation> getRepositories();
}
