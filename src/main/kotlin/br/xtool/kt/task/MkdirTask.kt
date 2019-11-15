package br.xtool.kt.task

import br.xtool.annotation.Task
import br.xtool.core.Console
import br.xtool.kt.core.AbstractTask
import org.springframework.beans.factory.annotation.Autowired

/**
 * Tarefa de criação de diretório
 */
@Task(type = "mkdir")
class MkdirTask(@Autowired private val console: Console): AbstractTask(console) {

    override fun process() {
        console.debug("\tMkdirTask.process()")
    }

    override fun validate(): Boolean {
        console.debug("\tMkdirTask.validate()")
        return false
    }
}