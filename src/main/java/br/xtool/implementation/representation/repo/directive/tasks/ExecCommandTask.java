package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.TaskRepresentation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@ToString(callSuper = true)
public class ExecCommandTask extends TaskRepresentation {

    private Collection<String> args;

    public Collection<String> getArgs() {
        return args;
    }

    public void setArgs(Collection<String> args) {
        this.args = args;
    }
}
