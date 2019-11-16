package br.xtool.command;

import br.xtool.context.WorkspaceContext;
import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.representation.ProjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "project", description = "Comando de gerenciamento de projetos do workspace")
public class ProjectCommand extends AbstractCommand {

    @CommandLine.Option(names = "--list", description = "Lista todos os projetos do workspace")
    private boolean listProjectsOption;

    @Autowired
    private WorkspaceContext workspaceContext;

    @Autowired
    private Console console;

    @Override
    public void run() {
        if (listProjectsOption) {
            printProjectList();
            return;
        };
        console.println(new CommandLine(this).getUsageMessage());
    }

    private void printProjectList() {
        if (listProjectsOption) {
            console.println("total %d", workspaceContext.getWorkspace().getProjects().size());
            workspaceContext.getWorkspace().getProjects().stream()
                    .forEach(p -> console.println("@|blue %s|@ -> %s", ((ProjectRepresentation) p).getName(), ((ProjectRepresentation) p).getType()));
            return;
        }
    }
}
