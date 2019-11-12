package br.xtool.core;

import br.xtool.representation.repo.RepositoryRepresentation;
import br.xtool.representation.repo.directive.XDescriptorRepresentation;
import picocli.CommandLine;

/**
 * Contexto do repositório xtool.
 *
 * @author jcruz
 */
public interface RepositoryContext {

    /**
     * Retorna o repositório de componentes.
     *
     * @return RepositoryContext
     */
    RepositoryRepresentation getRepository();

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


    /**
     * Cria o CommandSpec a partir do descritor.
     *
     * @param descriptor
     * @return
     */
    CommandLine.Model.CommandSpec create(XDescriptorRepresentation descriptor);

}
