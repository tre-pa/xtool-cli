package br.xtool.kt.impl.directive

import br.xtool.kt.ext.XDescriptorValidator
import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.XDefRepresentation
import br.xtool.representation.repo.directive.XDescriptorRepresentation
import org.yaml.snakeyaml.Yaml
import java.nio.file.Files

class XDescriptorRepresentationImpl private constructor(
        private val component: ComponentRepresentation,
        private val descriptor: Map<String, Any>) : XDescriptorRepresentation {

    override fun getXDef(): XDefRepresentation {
        return XDefRepresentationImpl(this.descriptor["def"] as Map<String, Any>, this)
    }

    override fun getComponent() = this.component;

    companion object {
        fun of(component: ComponentRepresentation): XDescriptorRepresentation {
            val descriptor: Map<String, Any> = Yaml().load(Files.newBufferedReader(component.path.resolve(XDescriptorRepresentation.DESCRIPTOR_FILENAME)));
            XDescriptorValidator.validate(descriptor, component);
            return XDescriptorRepresentationImpl(component, descriptor);
        }
    }
}

