package br.xtool.implementation.representation.repo.directive;

import br.xtool.representation.repo.directive.DescriptorParamRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
public class DescriptorParamRepresentationImpl implements DescriptorParamRepresentation {

    private String id;

    private String label;

    private String descriptor;

    private boolean required;

    private String type;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.descriptor;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
