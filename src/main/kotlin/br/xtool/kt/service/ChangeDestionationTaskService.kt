package br.xtool.kt.service

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.context.WorkspaceContext
import br.xtool.implementation.representation.repo.directive.tasks.ChangeDestinationTask
import br.xtool.kt.core.AbstractTaskService
import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.TaskRepresentation
import org.springframework.beans.factory.annotation.Autowired

@TaskService("change-destination")
class ChangeDestionationTaskService: AbstractTaskService() {

    @Autowired
    lateinit var workspaceContext: WorkspaceContext

    override fun run(ctx: ComponentExecutionContext, component: ComponentRepresentation, task: TaskRepresentation) {
        val wTask = task as ChangeDestinationTask
        ctx.destination = wTask.args.path
        logHeader("dest", "${workspaceContext.workspace.path.resolve(ctx.destination)}")
    }
}