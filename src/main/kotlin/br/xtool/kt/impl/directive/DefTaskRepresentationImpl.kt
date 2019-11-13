package br.xtool.kt.impl.directive

import br.xtool.representation.repo.directive.DefTaskRepresentation
import java.util.*

class DefTaskRepresentationImpl(private val task: Map<String, Any>): DefTaskRepresentation {
    override fun getName() = task["name"] as String

    override fun isOnly()= Optional.ofNullable(task["only"] as String?)

    override fun getTask() = task.minus(arrayOf("name", "only"))

    override fun toString(): String {
        return "XTaskRepresentationImpl(task=$task)"
    }


}