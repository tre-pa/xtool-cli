package br.xtool.command.completer;

import br.xtool.core.Fabricable;
import br.xtool.xtoolcore.context.WorkspaceContext;
import br.xtool.xtoolcore.representation.ProjectRepresentation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.stream.Collectors;

@Component
public class ProjectRepresentationCompleter implements Iterable<String>, Fabricable {

    @Autowired
    private WorkspaceContext workspaceContext;

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return this.workspaceContext.getWorkspace().getProjects()
                .stream()
                .map(ProjectRepresentation::getName)
                .collect(Collectors.toList())
                .iterator();
    }
}
