package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.DescriptorTaskRepresentation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class ChangeDestinationTask extends DescriptorTaskRepresentation {

    private CreateDirTask.Args args;

    @Data
    public static class Args {
        private String path;
    }
}
