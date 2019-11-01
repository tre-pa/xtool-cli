package br.xtool.implementation.representation.repo.directive;

import br.xtool.representation.repo.DescriptorRepresentation;
import br.xtool.representation.repo.directive.ComponentDirectiveRepresentation;
import br.xtool.representation.repo.directive.ParamDirectiveRepresentation;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;

public class ComponentDirectiveRepresentationImpl implements ComponentDirectiveRepresentation {

    private DescriptorRepresentation descriptor;

    private Map<String, Object> componentDirectiveMap;

    private ComponentDirectiveRepresentationImpl(DescriptorRepresentation descriptor, Map<String, Object> componentDirectiveMap) {
        this.descriptor = descriptor;
        this.componentDirectiveMap = componentDirectiveMap;
    }

    @Override
    public String getDescription() {
        return (String) this.componentDirectiveMap.get("description");
    }

    @Override
    public String getVersion() {
        return String.valueOf(this.componentDirectiveMap.get("version"));
    }

    @Override
    public DescriptorRepresentation getDescriptor() {
        return this.descriptor;
    }

    @Override
    public List<ParamDirectiveRepresentation> getParamsDirective() {
        List<Map<String, Object>> params = (List<Map<String, Object>>) componentDirectiveMap.get("params");
//        return ParamDirectiveRepresentationImpl.of(this, (Map<String, Object>)cmpDirectiveMap.get("params"));
        return null;
    }

    @Override
    public CommandLine.Model.CommandSpec getCommandSpec() {
        CommandLine.Model.CommandSpec componentSpec = CommandLine.Model.CommandSpec.create()
				.name(this.getDescriptor().getComponent().getName())
                .version(getVersion());
        componentSpec.usageMessage()
                .description(getDescription());
        componentSpec.mixinStandardHelpOptions(true);
        return componentSpec;
    }

    public static ComponentDirectiveRepresentation of(DescriptorRepresentation descriptor, Map<String, Object> cmpDirectiveMap) {
        ComponentDirectiveRepresentationImpl impl = new ComponentDirectiveRepresentationImpl(descriptor,cmpDirectiveMap);
        return impl;
    }
}
