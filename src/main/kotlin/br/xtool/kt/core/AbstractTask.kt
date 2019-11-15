package br.xtool.kt.core

import br.xtool.core.Console
import br.xtool.core.DescriptorContext
import br.xtool.representation.repo.directive.TaskDefRepresentation
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractTask {

    @Autowired
    lateinit var console: Console

    abstract fun process(task: TaskDefRepresentation, ctx: DescriptorContext)

    abstract fun validate()

    fun exec(task: TaskDefRepresentation, ctx: DescriptorContext): Unit  {
        console.debug("${this::class.java.simpleName}.exec()")
        validate()
        console.println("@|blue [TASK]|@ --- @|yellow ${ctx.parse(task.name)}|@ ---")
        process(task, ctx)
    }
}