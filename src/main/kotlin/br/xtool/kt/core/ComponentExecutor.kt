package br.xtool.kt.core

import br.xtool.annotation.TaskService
import br.xtool.context.ComponentExecutionContext
import br.xtool.core.Console
import br.xtool.representation.repo.ComponentRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ComponentExecutor(@Autowired val console: Console,
                        @Autowired val taskSet: Set<AbstractTaskService>) {

    val tasks = taskSet.asSequence()
            .map { task -> task::class.java.getAnnotation(TaskService::class.java).value to task }
            .toMap()

    fun run(component: ComponentRepresentation, componentExecutionContext: ComponentExecutionContext): Unit {

        console.debug("ComponentExecutor.run(name: ${component.name}, tasks: ${component.componentDescriptor.tasks.size})")
        component.componentDescriptor.tasks.forEach {
            tasks[it.type]?.init(componentExecutionContext, component ,it)
            println()
        }
    }

}