package br.xtool.representation.repo.directive;

import picocli.CommandLine;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Classe que representa a directiva 'component' do arquivo descritor.
 */
public interface XComponentRepresentation {

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
     * Retorna a representação do arquivo descriptor
     *
     * @return
     */
    XDescriptorRepresentation getDescriptor();

    /**
     * Retorna a lista de directivas de paramentros.
     *
     * @return
     */
    Collection<XParamRepresentation> getXParams();


    /**
     * Retorna o CommandSpec do component.
     *
     * @return
     */
    CommandLine.Model.CommandSpec getCommandSpec();

}
