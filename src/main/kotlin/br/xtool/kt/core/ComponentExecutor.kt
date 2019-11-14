package br.xtool.kt.core

import br.xtool.core.AbstractTask
import br.xtool.core.Console
import br.xtool.representation.repo.ComponentRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ComponentExecutor(@Autowired val console: Console,
                        @Autowired @Qualifier("tasks") val tasks: Map<String, AbstractTask>) {

    fun run(component: ComponentRepresentation): Unit {
        console.debug("ComponentExecutor.run(name: ${component.name}, tasks: ${component.descriptor.def.tasks.size})")
        component.descriptor.def.tasks.forEach { tasks[it.type]?.exec(it) }
    }

}