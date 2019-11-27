package br.xtool.command;

import br.xtool.context.WorkspaceContext;
import br.xtool.command.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.representation.ProjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "workspace", description = "Comando de gerenciamento de workspace")
public class WorkspaceCommand extends AbstractCommand {

    @CommandLine.Option(names = "--projects", description = "Lista todos os projetos do workspace")
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
