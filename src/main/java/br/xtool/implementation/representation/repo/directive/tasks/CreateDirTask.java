package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.DescriptorTaskRepresentation;
import com.sun.org.apache.xpath.internal.Arg;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString(callSuper = true)
@Getter
public class CreateDirTask extends DescriptorTaskRepresentation {

    private Args args;

    @Data
    public static class Args {
        private String path;
    }
}
