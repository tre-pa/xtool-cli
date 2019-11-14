package br.xtool.kt.impl.directive

import br.xtool.representation.repo.directive.TaskDefRepresentation
import java.util.*

class TaskDefRepresentationImpl(private val taskDef: Map<String, Any>): TaskDefRepresentation {
    override fun getName() = taskDef["name"] as String

    override fun getType() =  this.task.toList().first().first

    override fun isOnly()= Optional.ofNullable(taskDef["only"] as String?)

    override fun getTask() = taskDef - arrayOf("name", "only")

    override fun toString(): String {
        return "XTaskRepresentationImpl(task=$taskDef)"
    }


}