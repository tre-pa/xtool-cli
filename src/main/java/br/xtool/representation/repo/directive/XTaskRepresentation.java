package br.xtool.representation.repo.directive;

import java.util.Map;
import java.util.Optional;

/**
 * Representação de uma tarefa.
 */
public interface XTaskRepresentation {

    /**
     * Retorna o nome da tarefa.
     *
     * @return
     */
    String getName();

    /**
     * Retorna a expressão para a execução da tarefa.
     *
     * @return
     */
    Optional<String> isOnly();

    /**
     * Retorna os itens da tarefa.
     *
     * @return
     */
    Map<String, Object> getItems();
}
