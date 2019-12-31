package br.xtool.kt.core

import br.xtool.context.ComponentExecutionContext
import br.xtool.core.Console
import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.TaskRepresentation
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractTaskService() {

    @Autowired lateinit var console: Console

    fun init(ctx: ComponentExecutionContext, component: ComponentRepresentation , task: TaskRepresentation):Unit {
        val only = ctx.parseAsBoolean(task.only)
        val name = ctx.parse(task.name)
        if(only) {
            console.println("[@|blue,bold TASK|@] -- @|white,bold ${name}|@ (${task.type}) --")
            run(ctx, component ,task)
        }
    }

    fun logHeader(key: String, msg: String) {
        console.println(" @|white,bold ${key}|@: ${msg}")
    }

    fun log(msg: String) {
        console.println(" - ${msg}")
    }

    abstract fun run(ctx: ComponentExecutionContext , component: ComponentRepresentation , task: TaskRepresentation)
}