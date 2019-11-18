package br.xtool.kt.task

import br.xtool.annotation.Task
import br.xtool.context.TaskContext
import br.xtool.context.WorkspaceContext
import br.xtool.kt.core.AbstractTask
import br.xtool.representation.repo.directive.TaskDefRepresentation
import org.springframework.beans.factory.annotation.Autowired

@Task(type = "tpl")
class TplTask(@Autowired val workspaceContext: WorkspaceContext): AbstractTask() {
    override fun process(taskContext: TaskContext) {
        val taskParams: Map<String, Any> = taskContext.taskDef.task["tpl"] as Map<String, Any>
        val src: String = taskParams["src"] as String

    }

    override fun validate(taskDef: TaskDefRepresentation) {
    }
}