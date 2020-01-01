package br.xtool.representation.repo.directive;

import br.xtool.implementation.representation.repo.directive.tasks.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateDirTask.class, name = "create-dir"),
        @JsonSubTypes.Type(value = ChangeDestinationTask.class, name = "change-destination"),
        @JsonSubTypes.Type(value = CopyTemplateTask.class, name = "copy-template"),
        @JsonSubTypes.Type(value = ExecCommandTask.class, name = "exec-command"),
        @JsonSubTypes.Type(value = ReadClassTask.class, name ="read-class"),
        @JsonSubTypes.Type(value = ReadPlantumlTask.class, name = "read-plantuml")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public abstract class TaskRepresentation {
    private String name;

    private String type;

    private String only = "true";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOnly() {
        return only;
    }

    public void setOnly(String only) {
        this.only = only;
    }
}
