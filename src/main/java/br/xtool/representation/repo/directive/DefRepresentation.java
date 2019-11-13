package br.xtool.representation.repo.directive;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Optional;

/**
 * Classe que representa a definição do 'component'.
 */
public interface DefRepresentation {

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
    Collection<DefParamRepresentation> getParams();

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
     * Retorna as tarefas do compoenente
     *
     * @return
     */
    Collection<DefTaskRepresentation> getTasks();

}
