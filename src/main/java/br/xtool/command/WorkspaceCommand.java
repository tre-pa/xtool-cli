package br.xtool.command;

import br.xtool.annotation.CoreCommand;
import br.xtool.command.core.AbstractCommand;
import br.xtool.context.WorkspaceContext;
import br.xtool.core.Console;
import br.xtool.representation.ProjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

@CoreCommand
@CommandLine.Command(name = "workspace", description = "Comando de gerenciamento de workspace")
public class WorkspaceCommand extends AbstractCommand {

    @CommandLine.Option(names = "--projects", description = "Lista todos os projetos do workspace")
    private boolean listProjectsOption;

    @Autowired
    private WorkspaceContext workspaceContext;

    @Autowired
    private Console console;

    public void printProjectList() {
        if (listProjectsOption) {
            console.println("total %d", workspaceContext.getWorkspace().getProjects().size());
            workspaceContext.getWorkspace().getProjects().stream()
                    .forEach(p -> console.println("@|blue %s|@ -> %s (%s)", ((ProjectRepresentation) p).getName(), ((ProjectRepresentation) p).getType(), ((ProjectRepresentation) p).getFrameworkVersion()));
            return;
        }
    }
}
