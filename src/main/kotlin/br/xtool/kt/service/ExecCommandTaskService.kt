package br.xtool.kt.service

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.context.WorkspaceContext
import br.xtool.implementation.representation.repo.directive.tasks.ExecCommandTask
import br.xtool.kt.core.AbstractTaskService
import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.TaskRepresentation
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import java.nio.file.Path

@TaskService("exec-command")
class ExecCommandTaskService: AbstractTaskService() {

    @Autowired
    lateinit var workspaceContext: WorkspaceContext

    override fun run(ctx: ComponentExecutionContext, component: ComponentRepresentation, task: TaskRepresentation) {
        val wTask = task as ExecCommandTask
        val finalDest = workspaceContext.workspace.path.resolve(ctx.destination)
        logHeader("dest","${finalDest}")
        wTask.args.asSequence()
                .map { ctx.parseAsString(it) }
                .forEach { runSH(it, finalDest) }

    }

    fun runSH(cmd: String, finalDest: Path) : Unit {
        val processBuilder = ProcessBuilder()
        processBuilder.command("sh", "-c", cmd)
        processBuilder.directory(finalDest.toFile())
//        processBuilder.inheritIO()
        val process = processBuilder.start()
        process.waitFor()
        log("${cmd}")
    }
}