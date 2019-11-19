package br.xtool.representation.repo.directive;

import org.apache.commons.lang3.tuple.Pair;
import picocli.CommandLine;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Classe que representa a definição do 'component'.
 */
public interface ComponentDefRepresentation {

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
    DescriptorYmlRepresentation getDescriptorYml();

    /**
     * Retorna a lista de directivas de paramentros.
     *
     * @return
     */
    Collection<ParamDefRepresentation> getParams();


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
    Collection<TaskDefRepresentation> getTasks();

    /**
     * Retorna o parametro pelo nome.
     *
     * @param label
     * @return
     */
    ParamDefRepresentation findParamByLabel(String label);

    /**
     * Retorna os valores dos parametros.
     *
     * @param parseResult
     * @return
     */
    Map<String, Object> getParamDefValues(CommandLine.ParseResult parseResult);
}
