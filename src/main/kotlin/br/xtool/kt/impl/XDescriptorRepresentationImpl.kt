package br.xtool.kt.impl

import br.xtool.representation.repo.ComponentRepresentation
import br.xtool.representation.repo.directive.XDefRepresentation
import br.xtool.representation.repo.directive.XDescriptorRepresentation
import org.yaml.snakeyaml.Yaml
import java.lang.IllegalArgumentException
import java.nio.file.Files
import kotlin.collections.HashMap

class XDescriptorRepresentationImpl(private val component: ComponentRepresentation) : XDescriptorRepresentation {

    private val descriptor: Map<String, Any> = Yaml().load(Files.newBufferedReader(this.component.path.resolve(XDescriptorRepresentation.DESCRIPTOR_FILENAME)));

    val componentDerectiveNotFoundMessage = "Declaração da diretiva 'def' não encontrada no arquivo ${this.component.path}";

    override fun getXDef(): XDefRepresentation {
        val componentDirective = this.descriptor["def"] ?: throw IllegalArgumentException(componentDerectiveNotFoundMessage);
        return XDefRepresentationImpl(componentDirective as Map<String, Any>, this)
    }

    override fun getComponent() = this.component;
}

