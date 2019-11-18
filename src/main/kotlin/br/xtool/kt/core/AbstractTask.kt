package br.xtool.kt.core

import br.xtool.context.TaskContext
import br.xtool.core.Console
import br.xtool.representation.repo.directive.TaskDefRepresentation
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractTask {

    @Autowired
    lateinit var console: Console

    abstract fun process(taskContext: TaskContext)

    abstract fun validate(taskDef: TaskDefRepresentation)

    fun exec(taskContext: TaskContext): Unit  {
        console.debug("${this::class.java.simpleName}.exec()")
        validate(taskContext.taskDef)
        console.println("@|bold,blue [TASK]|@ --- @|yellow ${taskContext.descriptorContext.parse(taskContext.taskDef.name)}|@ @|bold,white (${taskContext.taskDef.type})|@ ---")
        process(taskContext)
    }
}