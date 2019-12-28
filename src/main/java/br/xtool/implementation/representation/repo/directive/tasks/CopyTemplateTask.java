package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.DescriptorTaskRepresentation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CopyTemplateTask extends DescriptorTaskRepresentation {

    private Args args;

    @Data
    public static class Args {
        private String src;

        private String dest;
    }
}
