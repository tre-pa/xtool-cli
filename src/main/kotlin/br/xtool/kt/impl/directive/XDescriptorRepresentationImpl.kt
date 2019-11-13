package br.xtool.kt.impl.directive

import br.xtool.kt.ext.XDescriptorValidator
import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.DefRepresentation
import br.xtool.representation.repo.directive.DescriptorRepresentation
import org.yaml.snakeyaml.Yaml
import java.nio.file.Files

class XDescriptorRepresentationImpl private constructor(
        private val component: ComponentRepresentation,
        private val descriptor: Map<String, Any>) : DescriptorRepresentation {

    override fun getXDef(): DefRepresentation {
        return DefRepresentationImpl(this.descriptor["def"] as Map<String, Any>, this)
    }

    override fun getComponent() = this.component;

    companion object {
        fun of(component: ComponentRepresentation): DescriptorRepresentation {
            val descriptor: Map<String, Any> = Yaml().load(Files.newBufferedReader(component.path.resolve(DescriptorRepresentation.DESCRIPTOR_FILENAME)));
            XDescriptorValidator.validate(descriptor, component);
            return XDescriptorRepresentationImpl(component, descriptor);
        }
    }
}

