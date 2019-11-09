package br.xtool.kt.impl

import br.xtool.representation.repo.directive.XDefRepresentation
import br.xtool.representation.repo.directive.XDescriptorRepresentation
import br.xtool.representation.repo.directive.XParamRepresentation
import org.apache.commons.lang3.tuple.Pair
import picocli.CommandLine
import java.util.*


class XDefRepresentationImpl(
        private val def: Map<String, Any>,
        private val descriptor: XDescriptorRepresentation): XDefRepresentation {

    override fun getDescription() = def["description"] as String

    override fun getVersion() = def["version"] as String

    override fun getDescriptor() = this.descriptor;

    override fun getXParams(): MutableCollection<XParamRepresentation> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDepends(): Optional<String> = Optional.ofNullable(def["depends"] as String?);

    override fun getAvailability(): Optional<Pair<String, String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCommandSpec(): CommandLine.Model.CommandSpec {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}