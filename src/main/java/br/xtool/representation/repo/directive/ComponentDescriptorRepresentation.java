package br.xtool.representation.repo.directive;

import java.util.Collection;

/**
 * Representação do descritor de componente (xtool.yml)
 */
public interface ComponentDescriptorRepresentation {

    /**
     * Retorna o nome do componente.
     *
     * @return
     */
    String getName();

    /**
     * Retorna a descrição do componente
     *
     * @return
     */
    String getDescription();

    /**
     * Retorna a versão do componente.
     *
     * @return
     */
    String getVersion();

    /**
     * Retorna a lista de paramentros do descritor.
     *
     * @return
     */
    Collection<DescriptorParamRepresentation> getParams();

    /**
     * Retorna a lista de tarefas do descritor.
     *
     * @return
     */
    Collection<DescriptorTaskRepresentation> getTasks();

}

