package br.xtool.implementation.representation.repo.directive;

import br.xtool.representation.repo.directive.ComponentDescriptorRepresentation;
import br.xtool.representation.repo.directive.DescriptorParamRepresentation;
import br.xtool.representation.repo.directive.TaskRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;

import java.util.Collection;


@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentDescriptorRepresentationImpl implements ComponentDescriptorRepresentation {

    private String name;

    private String description;

    private String version;

    private Collection<DescriptorParamRepresentation> params;

    private Collection<TaskRepresentation> tasks;

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
    public Collection<TaskRepresentation> getTasks() {
        return this.tasks;
    }
}
