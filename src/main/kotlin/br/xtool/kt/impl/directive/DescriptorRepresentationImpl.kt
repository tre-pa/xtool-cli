package br.xtool.kt.impl.directive

import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.ComponentDefRepresentation
import br.xtool.representation.repo.directive.DescriptorYmlRepresentation
import org.yaml.snakeyaml.Yaml
import java.nio.file.Files

class DescriptorRepresentationImpl private constructor(
        private val component: ComponentRepresentation,
        private val descriptor: Map<String, Any>) : DescriptorYmlRepresentation {

    override fun getComponentDef(): ComponentDefRepresentation {
        return DefRepresentationImpl(this.descriptor["def"] as Map<String, Any>, this)
    }

    override fun getComponent() = this.component;

    companion object {
        fun of(component: ComponentRepresentation): DescriptorYmlRepresentation {
            val descriptor: Map<String, Any> = Yaml().load(Files.newBufferedReader(component.path.resolve(DescriptorYmlRepresentation.DESCRIPTOR_FILENAME)));
            return DescriptorRepresentationImpl(component, descriptor);
        }
    }
}

