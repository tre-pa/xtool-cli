package br.xtool.kt.impl.directive

import br.xtool.representation.repo.directive.XDefRepresentation
import br.xtool.representation.repo.directive.XDescriptorRepresentation
import br.xtool.representation.repo.directive.XParamRepresentation
import br.xtool.representation.repo.directive.XTaskRepresentation
import org.apache.commons.lang3.tuple.Pair
import java.util.*


class XDefRepresentationImpl(
        private val def: Map<String, Any>,
        private val descriptor: XDescriptorRepresentation): XDefRepresentation {


    override fun getDescription() = def["description"] as String

    override fun getVersion() = def["version"] as String

    override fun getDescriptor() = this.descriptor

    override fun getParams(): MutableCollection<XParamRepresentation> {
        val params: List<Map<String, Any>> = def["params"] as List<Map<String, Any>>
        return params.map(::XParamRepresentationImpl).toMutableList()
    }

    override fun getDepends() = Optional.ofNullable(def["depends"] as String?)

    override fun getAvailability(): Optional<Pair<String, String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTasks(): MutableCollection<XTaskRepresentation> {
        val tasks: List<Map<String, Any>> = def["tasks"] as List<Map<String, Any>>
        return tasks.map(::XTaskRepresentationImpl).toMutableList();
    }
}