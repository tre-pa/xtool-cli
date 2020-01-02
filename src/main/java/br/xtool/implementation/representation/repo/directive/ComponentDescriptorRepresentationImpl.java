package br.xtool.implementation.representation.repo.directive;

import br.xtool.representation.repo.directive.ComponentDescriptorRepresentation;
import br.xtool.representation.repo.directive.DescriptorParamRepresentation;
import br.xtool.representation.repo.directive.TaskRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private ComponentDescriptorEnabledRepresentation enabled;

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

    @Override
    public ComponentDescriptorEnabledRepresentation getEnabled() {
        return this.enabled;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ComponentDescriptorEnabledRepresentationImpl implements ComponentDescriptorEnabledRepresentation {

        private String when;

        @JsonProperty("on_fail")
        private String onFail;

        @Override
        public String getWhen() {
            return this.when;
        }

        @Override
        public String onFail() {
            return this.onFail;
        }
    }
}
