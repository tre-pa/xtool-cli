package br.xtool.representation.repo.directive;

import org.apache.commons.lang3.tuple.Pair;
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
     * Retorna o componente de dependência.
     *
     * @return
     */
    Optional<String> getDepends();

    /**
     * Retorna a disponibilidade do componente.
     *
     * @return Pair<String, String> com o paramentro left sendo a condição e o right a mensagem em caso de falha.
     */
    Optional<Pair<String, String>> getAvailability();

    /**
     * Retorna o CommandSpec do component.
     *
     * @return
     */
    CommandLine.Model.CommandSpec getCommandSpec();


}
