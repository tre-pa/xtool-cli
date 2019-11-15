package br.xtool.kt.task

import br.xtool.annotation.Task
import br.xtool.core.DescriptorContext
import br.xtool.kt.core.AbstractTask
import br.xtool.representation.repo.directive.TaskDefRepresentation

/**
 * Tarefa de criação de diretório
 */
@Task(type = "mkdir")
class MkdirTask: AbstractTask() {

    override fun process(task: TaskDefRepresentation, ctx: DescriptorContext) {
        console.debug(">>> MkdirTask.process()")
    }

    override fun validate() {
        console.debug(">>> MkdirTask.validate()")
    }
}