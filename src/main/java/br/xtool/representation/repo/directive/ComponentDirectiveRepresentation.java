package br.xtool.representation.repo.directive;

import br.xtool.representation.repo.DescriptorRepresentation;
import picocli.CommandLine;

import java.util.List;

/**
 * Classe que representa a directiva 'component' do arquivo descritor.
 */
public interface ComponentDirectiveRepresentation {

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
    DescriptorRepresentation getDescriptor();

    /**
     * Retorna a lista de directivas de paramentros.
     *
     * @return
     */
    List<ParamDirectiveRepresentation> getParamsDirective();


    /**
     * Retorna o CommandSpec do component.
     *
     * @return
     */
    CommandLine.Model.CommandSpec getCommandSpec();

}
