package br.xtool.context;

import br.xtool.xtoolcore.representation.repo.RepositoryRepresentation;

/**
 * Contexto do repositório xtool.
 *
 * @author jcruz
 */
public interface RepositoryContext {

//    /**
//     * Retorna o repositório de componentes.
//     *
//     * @return RepositoryContext
//     */
//    List<RepositoryRepresentation> getRepositories();

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

//
//    /**
//     * Cria o CommandSpec a partir do descritor.
//     *
//     * @param descriptor
//     * @return
//     */
//    CommandLine.Model.CommandSpec create(ComponentDescriptorRepresentation descriptor);


//    /**
//     * Retorna o componente pelo nome.
//     *
//     * @param name
//     * @return
//     */
//    Optional<ComponentRepresentation> findComponentByName(String name);


//    /**
//     * Retorna a quantidade total de repositórios.
//     *
//     * @return
//     */
//    long getTotalRepositories();

}
