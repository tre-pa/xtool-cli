package br.xtool.representation.repo;

import br.xtool.representation.repo.directive.ComponentDirectiveRepresentation;

import java.nio.file.Path;

/**
 * Classe que representa o arquivo descritor xtool.yml
 */
public interface DescriptorRepresentation {

    /**
     * Nome do arquivo descritor xtool.
     */
    static String DESCRIPTOR_FILENAME = "xtool.yml";

    /**
     * Retorna o componente descritor
     *
     * @return
     */
    ComponentDirectiveRepresentation getComponentDirective();


    /**
     * Retorna o componente.
     *
     * @return
     */
    ComponentRepresentation getComponent();

}
