package br.xtool.kt.service

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.context.WorkspaceContext
import br.xtool.implementation.representation.repo.directive.tasks.CopyTemplateTask
import br.xtool.kt.core.AbstractTaskService
import br.xtool.representation.repo.directive.TaskRepresentation
import org.springframework.beans.factory.annotation.Autowired

@TaskService("copy-template")
class CopyTemplateTaskService: AbstractTaskService() {

    @Autowired
    lateinit var workspaceContext: WorkspaceContext

    override fun run(ctx: ComponentExecutionContext, task: TaskRepresentation) {
        val wTask = task as CopyTemplateTask
        log("dest: ${workspaceContext.workspace.path.resolve(ctx.destination)}")
    }
}