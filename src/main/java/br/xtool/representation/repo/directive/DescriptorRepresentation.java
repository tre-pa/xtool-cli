package br.xtool.representation.repo.directive;

import br.xtool.representation.repo.ComponentRepresentation;

/**
 * Classe que representa o arquivo descritor xtool.yml
 */
public interface DescriptorRepresentation {

    /**
     * Nome do arquivo descritor xtool.
     */
    static String DESCRIPTOR_FILENAME = "xtool.yml";

    /**
     * Retorna a definição do componente.
     *
     * @return
     */
    DefRepresentation getDef();


    /**
     * Retorna o componente.
     *
     * @return
     */
    ComponentRepresentation getComponent();

}
