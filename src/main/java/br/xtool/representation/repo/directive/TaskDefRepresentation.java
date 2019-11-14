package br.xtool.representation.repo.directive;

import java.util.Map;
import java.util.Optional;

/**
 * Representação de uma tarefa.
 */
public interface TaskDefRepresentation {

    /**
     * Retorna o nome da tarefa.
     *
     * @return
     */
    String getName();

    /**
     * Retorna o tipo da tarefa.
     *
     * @return String com o tipo da tarefa.
     */
    String getType();

    /**
     * Retorna a expressão para a execução da tarefa.
     *
     * @return
     */
    Optional<String> isOnly();

    /**
     * Retorna o mapa com a terefa;
     *
     * @return
     */
    Map<String, Object> getTask();


}
