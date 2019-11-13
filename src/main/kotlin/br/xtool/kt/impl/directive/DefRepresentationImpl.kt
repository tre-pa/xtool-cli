package br.xtool.kt.impl.directive

import br.xtool.representation.repo.directive.DefParamRepresentation
import br.xtool.representation.repo.directive.DefRepresentation
import br.xtool.representation.repo.directive.DefTaskRepresentation
import br.xtool.representation.repo.directive.DescriptorRepresentation
import org.apache.commons.lang3.tuple.Pair
import java.util.*


class DefRepresentationImpl(
        private val def: Map<String, Any>,
        private val descriptor: DescriptorRepresentation): DefRepresentation {


    override fun getDescription() = def["description"] as String

    override fun getVersion() = def["version"] as String

    override fun getDescriptor() = this.descriptor

    override fun getParams(): MutableCollection<DefParamRepresentation> {
        val params: List<Map<String, Any>> = def["params"] as List<Map<String, Any>>
        return params.map(::DefParamRepresentationImpl).toMutableList()
    }

    override fun getDepends() = Optional.ofNullable(def["depends"] as String?)

    override fun getAvailability(): Optional<Pair<String, String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTasks(): MutableCollection<DefTaskRepresentation> {
        val tasks: List<Map<String, Any>> = def["tasks"] as List<Map<String, Any>>
        return tasks.map(::DefTaskRepresentationImpl).toMutableList();
    }
}