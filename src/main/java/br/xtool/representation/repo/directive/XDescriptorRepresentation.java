package br.xtool.representation.repo.directive;

import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.directive.XComponentRepresentation;

/**
 * Classe que representa o arquivo descritor xtool.yml
 */
public interface XDescriptorRepresentation {

    /**
     * Nome do arquivo descritor xtool.
     */
    static String DESCRIPTOR_FILENAME = "xtool.yml";

    /**
     * Retorna o componente descritor
     *
     * @return
     */
    XComponentRepresentation getXComponent();


    /**
     * Retorna o componente.
     *
     * @return
     */
    ComponentRepresentation getComponent();

}
