package br.xtool.kt.task

import br.xtool.annotation.Task
import br.xtool.core.AbstractTask
import br.xtool.core.Console
import org.springframework.beans.factory.annotation.Autowired

@Task(type = "mkdir")
class MkdirTask(@Autowired val console: Console): AbstractTask() {

    override fun process() {
        console.debug("\tMkdirTask.process()")
    }

    override fun validate(): Boolean {
        console.debug("\tMkdirTask.validate()")
        return false
    }
}