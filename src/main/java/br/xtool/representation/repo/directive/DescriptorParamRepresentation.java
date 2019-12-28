package br.xtool.representation.repo.directive;

import br.xtool.implementation.representation.repo.directive.DescriptorParamRepresentationImpl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe que representa o paramentro dentro do descritor de componente.
 */
public interface DescriptorParamRepresentation {
    /**
     * Id do paramentros
     * @return
     */
    String getId();

    String getLabel();

    String getDescription();

    boolean isRequired();

    String getType();
}
