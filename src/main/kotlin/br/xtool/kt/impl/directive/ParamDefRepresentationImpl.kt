package br.xtool.kt.impl.directive

import br.xtool.representation.repo.directive.ParamDefRepresentation

class ParamDefRepresentationImpl(val param: Map<String,Any>) : ParamDefRepresentation {
    override fun getId() = param["id"] as String

    override fun getLabel() = param["label"] as String

    override fun getDescription() = param["description"] as String

    override fun isRequired() = param["required"] as Boolean? ?: false

    override fun getType() = when(param["type"] as String?) {
        "String" -> String::class.java
        "Boolean" -> Boolean::class.java
        else -> String::class.java
    }
}