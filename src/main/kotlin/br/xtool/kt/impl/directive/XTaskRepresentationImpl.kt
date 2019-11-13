package br.xtool.kt.impl.directive

import br.xtool.representation.repo.directive.XTaskRepresentation
import java.util.*

class XTaskRepresentationImpl(private val task: Map<String, Any>): XTaskRepresentation {
    override fun getName() = task["name"] as String

    override fun isOnly()= Optional.ofNullable(task["only"] as String?)

    override fun getTask() = task.minus(arrayOf("name", "only"))

    override fun toString(): String {
        return "XTaskRepresentationImpl(task=$task)"
    }


}