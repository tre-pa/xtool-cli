package br.xtool.implementation.representation.repo.directive.tasks;

import br.xtool.representation.repo.directive.TaskRepresentation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString(callSuper = true)
public class ExecCommandTask extends TaskRepresentation {

    private Collection<String> args;
}
