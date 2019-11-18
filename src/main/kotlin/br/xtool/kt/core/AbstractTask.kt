package br.xtool.kt.core

import br.xtool.core.Console
import br.xtool.core.DescriptorContext
import br.xtool.representation.repo.directive.TaskDefRepresentation
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractTask {

    @Autowired
    lateinit var console: Console

    abstract fun process(taskDef: TaskDefRepresentation, ctx: DescriptorContext)

    abstract fun validate(taskDef: TaskDefRepresentation)

    fun exec(taskDef: TaskDefRepresentation, ctx: DescriptorContext): Unit  {
        console.debug("${this::class.java.simpleName}.exec()")
        validate(taskDef)
        console.println("@|bold,blue [TASK]|@ --- @|yellow ${ctx.parse(taskDef.name)}|@ @|bold,white (${taskDef.type})|@ ---")
        process(taskDef, ctx)
    }
}