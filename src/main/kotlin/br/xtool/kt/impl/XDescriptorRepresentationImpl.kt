package br.xtool.kt.impl

import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.XDefRepresentation
import br.xtool.representation.repo.directive.XDescriptorRepresentation
import org.yaml.snakeyaml.Yaml
import java.nio.file.Files

class XDescriptorRepresentationImpl private constructor(
        private val component: ComponentRepresentation,
        private val descriptor: Map<String, Any>) : XDescriptorRepresentation {

    override fun getXDef(): XDefRepresentation {
        validateDef()
        return XDefRepresentationImpl(this.descriptor["def"] as Map<String, Any>, this)
    }

    override fun getComponent() = this.component;

    /* Valida se a diretiva 'def' é definida no descritor do componente */
    private fun validateDef(): Unit {
        this.descriptor["def"] ?: throw IllegalArgumentException("Diretiva 'def' não encontrado no componente ${component.name}")
    }

    companion object {
        fun of(component: ComponentRepresentation): XDescriptorRepresentation {
            val descriptor: Map<String, Any> = Yaml().load(Files.newBufferedReader(component.path.resolve(XDescriptorRepresentation.DESCRIPTOR_FILENAME)));
            return XDescriptorRepresentationImpl(component, descriptor);
        }
    }
}

