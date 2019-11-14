package br.xtool.context;

import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import br.xtool.representation.repo.directive.DescriptorRepresentation;
import picocli.CommandLine;

import java.util.Optional;

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
    CommandLine.Model.CommandSpec create(DescriptorRepresentation descriptor);


    /**
     * Retorna o componente pelo nome.
     *
     * @param name
     * @return
     */
    Optional<ComponentRepresentation> findComponentByName(String name);

}
