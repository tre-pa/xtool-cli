package br.xtool.kt.ext

import br.xtool.representation.repo.ComponentRepresentation

object XDescriptorValidator {
    fun validate(descriptor: Map<String, Any>, component: ComponentRepresentation): Unit {
        descriptor["def"] ?: throw IllegalArgumentException("Diretiva 'def' é obrigatória no componente ${component.name}")
        with(descriptor["def"] as Map<String, Any>) {
            this["description"] ?: throw java.lang.IllegalArgumentException("Diretiva 'description' é obrigatória no componente ${component.name}")
            
            
        }
    }
}