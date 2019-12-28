package br.xtool.implementation.representation.repo.directive;

import br.xtool.representation.repo.directive.ComponentDescriptorRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;


@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentDescriptorRepresentationImpl implements ComponentDescriptorRepresentation {

    private String name;

    private String description;

    private String version;

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
}
