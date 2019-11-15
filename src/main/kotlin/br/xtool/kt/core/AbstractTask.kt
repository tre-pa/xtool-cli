package br.xtool.kt.core

import br.xtool.core.Console
import br.xtool.representation.repo.directive.TaskDefRepresentation

abstract class AbstractTask (private val console: Console) {

    abstract fun process()

    abstract fun validate(): Boolean

    fun exec(task: TaskDefRepresentation): Unit  {
        console.debug("${this::class.java.simpleName}.exec()")
        validate()
        console.println("@|blue [Task]|@ --- @|yellow ${task.name}|@ ---")
        process()
    }
}