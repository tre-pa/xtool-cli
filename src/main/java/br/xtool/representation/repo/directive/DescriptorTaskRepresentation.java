package br.xtool.representation.repo.directive;

import br.xtool.command.ExecCommand;
import br.xtool.implementation.representation.repo.directive.tasks.ChangeDestinationTask;
import br.xtool.implementation.representation.repo.directive.tasks.CopyTemplateTask;
import br.xtool.implementation.representation.repo.directive.tasks.CreateDirTask;
import br.xtool.implementation.representation.repo.directive.tasks.ExecCommandTask;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateDirTask.class, name = "create-dir"),
        @JsonSubTypes.Type(value = ChangeDestinationTask.class, name = "change-destination"),
        @JsonSubTypes.Type(value = CopyTemplateTask.class, name = "copy-template"),
        @JsonSubTypes.Type(value = ExecCommandTask.class, name = "exec-command")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public abstract class DescriptorTaskRepresentation {
    private String name;

    private String type;
}
