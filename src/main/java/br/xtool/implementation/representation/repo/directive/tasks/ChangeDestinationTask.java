package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.TaskRepresentation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class ChangeDestinationTask extends TaskRepresentation {

    private CreateDirTask.Args args;

    public CreateDirTask.Args getArgs() {
        return args;
    }

    public void setArgs(CreateDirTask.Args args) {
        this.args = args;
    }

    public static class Args {
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
