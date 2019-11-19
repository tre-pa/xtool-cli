package br.xtool.kt.impl.directive

import br.xtool.representation.repo.directive.ComponentDefRepresentation
import br.xtool.representation.repo.directive.DescriptorYmlRepresentation
import br.xtool.representation.repo.directive.ParamDefRepresentation
import br.xtool.representation.repo.directive.TaskDefRepresentation
import org.apache.commons.lang3.tuple.Pair
import picocli.CommandLine
import java.util.*


class ComponentDefRepresentationImpl(
        private val def: Map<String, Any>,
        private val descriptorYml: DescriptorYmlRepresentation): ComponentDefRepresentation {

    override fun getDescription() = def["description"] as String

    override fun getVersion() = def["version"] as String

    override fun getDescriptorYml() = this.descriptorYml

    override fun getParams(): MutableCollection<ParamDefRepresentation> {
        val paramsDef: List<Map<String, Any>> = def["params"] as List<Map<String, Any>>
        return paramsDef.map(::ParamDefRepresentationImpl).toMutableList()
    }

    override fun getDepends() = Optional.ofNullable(def["depends"] as String?)

    override fun getAvailability(): Optional<Pair<String, String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTasks(): MutableCollection<TaskDefRepresentation> {
        val tasksDef: List<Map<String, Any>> = def["tasks"] as List<Map<String, Any>>
        return tasksDef.map(::TaskDefRepresentationImpl).toMutableList();
    }
    override fun findParamByLabel(label: String?) = this.params.find { it.label == label }

    override fun getParamDefValues(parseResult: CommandLine.ParseResult?) =
        parseResult?.subcommand()?.subcommand()?.matchedOptions()?.asSequence()?.
                map { op -> this.descriptorYml.componentDef.findParamByLabel(op.names()[0]).id to op.getValue<Any>() }?.
                toMap()

}