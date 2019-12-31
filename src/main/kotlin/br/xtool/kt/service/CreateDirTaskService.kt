package br.xtool.kt.service

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.context.WorkspaceContext
import br.xtool.implementation.representation.repo.directive.tasks.CreateDirTask
import br.xtool.kt.core.AbstractTaskService
import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.TaskRepresentation
import org.springframework.beans.factory.annotation.Autowired
import java.nio.file.Files

/**
 * Classe de service responsável pela execução da Task: create-dir
 */
@TaskService("create-dir")
class CreateDirTaskService: AbstractTaskService() {

    @Autowired lateinit var workspaceContext: WorkspaceContext

    override fun run(ctx: ComponentExecutionContext, component: ComponentRepresentation, task: TaskRepresentation) {
        val wTask = task as CreateDirTask
        val path = ctx.parseAsString(wTask.args.path)
        Files.createDirectories(workspaceContext.workspace.path.resolve(path))

        if(wTask.args.isCd) ctx.destination = path

        logHeader("path","${path}")
        logHeader("dest","${workspaceContext.workspace.path.resolve(ctx.destination)}")
    }
}