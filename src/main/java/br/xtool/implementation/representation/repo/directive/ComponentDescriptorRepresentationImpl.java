package br.xtool.implementation.representation.repo.directive;

import br.xtool.representation.repo.directive.ComponentDescriptorRepresentation;
import br.xtool.representation.repo.directive.DescriptorParamRepresentation;
import br.xtool.representation.repo.directive.DescriptorTaskRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import lombok.Setter;

import java.util.Collection;


@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentDescriptorRepresentationImpl implements ComponentDescriptorRepresentation {

    private String name;

    private String description;

    private String version;

    private Collection<DescriptorParamRepresentation> params;

    private Collection<DescriptorTaskRepresentation> tasks;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public Collection<DescriptorParamRepresentation> getParams() {
        return this.params;
    }

    @Override
    public Collection<DescriptorTaskRepresentation> getTasks() {
        return this.tasks;
    }
}
