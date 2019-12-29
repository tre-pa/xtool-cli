package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.TaskRepresentation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class ChangeDestinationTask extends TaskRepresentation {

    private CreateDirTask.Args args;

    @Data
    public static class Args {
        private String path;
    }
}
