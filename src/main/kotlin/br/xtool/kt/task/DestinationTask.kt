package br.xtool.kt.task

import br.xtool.annotation.Task
import br.xtool.context.WorkspaceContext
import br.xtool.core.DescriptorContext
import br.xtool.kt.core.AbstractTask
import br.xtool.representation.repo.directive.TaskDefRepresentation
import org.springframework.beans.factory.annotation.Autowired
import java.nio.file.Files
import java.nio.file.Path

/**
 * Tarefa de criação de diretório
 */
@Task(type = "destination")
class DestinationTask(@Autowired val workspaceContext: WorkspaceContext): AbstractTask() {

    override fun process(taskDef: TaskDefRepresentation, ctx: DescriptorContext) {
        console.debug(">>> MkdirTask.process()")
        val destination: String = taskDef.task["destination"] as String
        val path: Path = workspaceContext.workspace.path.resolve(ctx.parse(destination));
        if(Files.notExists(path)){
            Files.createDirectories(path);
            console.println("      @|green >|@ Criando diretório: ${path}")
        }
        ctx.updateDestination(path)
        console.println("      @|green >|@ Alterando diretório de destino para: ${path}")
    }

    override fun validate(taskDef: TaskDefRepresentation) {
        console.debug(">>> MkdirTask.validate()")
    }
}