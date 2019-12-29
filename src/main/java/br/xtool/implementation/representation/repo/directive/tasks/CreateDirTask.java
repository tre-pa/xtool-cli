package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.TaskRepresentation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class CreateDirTask extends TaskRepresentation {

    private Args args;

    public Args getArgs() {
        return args;
    }

    public void setArgs(Args args) {
        this.args = args;
    }

    public static class Args {
        private String path;

        private boolean cd = false;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isCd() {
            return cd;
        }

        public void setCd(boolean cd) {
            this.cd = cd;
        }
    }

}
