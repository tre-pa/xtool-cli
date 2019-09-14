package br.xtool.core;

import java.util.Set;

import br.xtool.representation.repo.RepositoryRepresentation;

/**
 * Contexto do repositório xtool.
 *
 * @author jcruz
 */
public interface RepositoryContext {

    /**
     * Retorna a lista de repositórios de componentes.
     *
     * @return RepositoryContext
     */
    Set<RepositoryRepresentation> getRepositories();

    /**
     * Retorna o repositório de trabalho.
     *
     * @return
     */
    RepositoryRepresentation getWorkingRepository();

    /**
     * Define o repositório de trabalho.
     *
     * @param repositoryRepresentation
     */
    void setWorkingRepository(RepositoryRepresentation repositoryRepresentation);
}
