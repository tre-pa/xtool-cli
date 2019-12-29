package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.TaskRepresentation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class CopyTemplateTask extends TaskRepresentation {

    private Args args;

    public Args getArgs() {
        return args;
    }

    public void setArgs(Args args) {
        this.args = args;
    }

    public static class Args {
        private String src;

        private String dest;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDest() {
            return dest;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }
    }
}
