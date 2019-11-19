package br.xtool.kt.core

import br.xtool.context.DescriptorContext
import br.xtool.context.TaskContext
import br.xtool.core.Console
import br.xtool.representation.repo.ComponentRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ComponentExecutor(@Autowired val console: Console,
                        @Autowired @Qualifier("tasks") val tasks: Map<String, AbstractTask>) {

    fun run(component: ComponentRepresentation, descriptorContext: DescriptorContext): Unit {
        console.debug("ComponentExecutor.run(name: ${component.name}, tasks: ${component.descriptor.componentDef.tasks.size})")

        component.descriptor.componentDef.tasks.forEach { tasks[it.type]?.exec(TaskContext(component, it, descriptorContext)) }
    }

}