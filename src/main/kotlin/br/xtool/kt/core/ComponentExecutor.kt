package br.xtool.kt.core

import br.xtool.context.ComponentExecutionContext
import br.xtool.core.Console
import br.xtool.representation.repo.ComponentRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ComponentExecutor(@Autowired val console: Console) {

    fun run(component: ComponentRepresentation, componentExecutionContext: ComponentExecutionContext): Unit {

        console.debug("ComponentExecutor.run(name: ${component.name}, tasks: ${component.componentDescriptor.tasks.size})")

        component.componentDescriptor.tasks.forEach({ console.debug(it.toString()) })

        //component.descriptor.componentDef.tasks.forEach { tasks[it.type]?.exec(TaskContext(component, it, descriptorContext)) }
    }

}