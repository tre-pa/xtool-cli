package br.xtool.kt

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.context.WorkspaceContext
import br.xtool.implementation.representation.repo.directive.tasks.ChangeDestinationTask
import br.xtool.implementation.representation.repo.directive.tasks.CreateDirTask
import br.xtool.kt.core.AbstractTaskService
import br.xtool.representation.repo.directive.TaskRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths

@TaskService("create-dir")
class CreateDirTaskService: AbstractTaskService() {

    @Autowired lateinit var workspaceContext: WorkspaceContext

    override fun run(ctx: ComponentExecutionContext, task: TaskRepresentation) {
        val ptask = task as CreateDirTask
        val path = ctx.parse(ptask.args.path)
        Files.createDirectories(workspaceContext.workspace.path.resolve(path))
        log("path: ${path}")
    }
}