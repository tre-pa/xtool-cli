package br.xtool.command.converter;

import br.xtool.annotation.Converter;
import br.xtool.context.WorkspaceContext;
import br.xtool.core.Fabricable;
import br.xtool.representation.ProjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

@Converter(ProjectRepresentation.class)
public class ProjectRepresentationConverter implements CommandLine.ITypeConverter<ProjectRepresentation>, Fabricable {

    @Autowired
    private WorkspaceContext workspaceContext;

    @Override
    public ProjectRepresentation convert(String value) throws Exception {
        return this.workspaceContext.getWorkspace().getProjects()
                .stream()
                .filter(p -> p.getName().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Projeto %s não encontrado no workspace.", value)));
    }
}
