package br.xtool.representation.repo.directive;

import java.util.Collection;

public interface XTaskGroupRepresentation {

    /**
     * Retorna o componente da tarefa.
     *
     * @return
     */
    XDefRepresentation getXComponent();

    /**
     * Retorna a lista de tarefas do grupo.
     *
     * @return
     */
    Collection<XTaskRepresentation> getXTasks();
}
