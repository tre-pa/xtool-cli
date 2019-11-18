package br.xtool.kt.task

import br.xtool.annotation.Task
import br.xtool.context.TaskContext
import br.xtool.context.WorkspaceContext
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

    override fun process(taskContext: TaskContext) {
        console.debug(">>> MkdirTask.process()")
        val destination: String = taskContext.taskDef.task["destination"] as String
        val path: Path = workspaceContext.workspace.path.resolve(taskContext.descriptorContext.parse(destination));
        if(Files.notExists(path)){
            Files.createDirectories(path);
            console.println("      @|green >|@ Criando diretório: ${path}")
        }
        taskContext.descriptorContext.updateDestination(path)
        console.println("      @|green >|@ Alterando diretório de destino para: ${path}")
    }

    override fun validate(taskDef: TaskDefRepresentation) {
        console.debug(">>> MkdirTask.validate()")
    }
}