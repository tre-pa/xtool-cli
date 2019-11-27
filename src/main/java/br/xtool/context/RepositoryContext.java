package br.xtool.context;

import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import br.xtool.representation.repo.directive.DescriptorYmlRepresentation;
import picocli.CommandLine;

import java.util.List;
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
    List<RepositoryRepresentation> getRepositories();

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
    CommandLine.Model.CommandSpec create(DescriptorYmlRepresentation descriptor);


    /**
     * Retorna o componente pelo nome.
     *
     * @param name
     * @return
     */
    Optional<ComponentRepresentation> findComponentByName(String name);

}
