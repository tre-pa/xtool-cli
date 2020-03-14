package br.xtool.command;

import br.xtool.annotation.CoreCommand;
import br.xtool.command.completer.ProjectRepresentationCompleter;
import br.xtool.command.converter.ProjectRepresentationConverter;
import br.xtool.command.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.xtoolcore.context.WorkspaceContext;
import br.xtool.xtoolcore.representation.ProjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

@CoreCommand
@CommandLine.Command(name = "workspace", description = "Comando de gerenciamento de workspace")
public class WorkspaceCommand extends AbstractCommand {

    @CommandLine.Option(names = "--projects", description = "Lista todos os projetos do workspace")
    private boolean listProjectsOption;

    @CommandLine.Option(names = "--use",
            description = "Define o projeto de trabalho",
            completionCandidates = ProjectRepresentationCompleter.class,
            converter = ProjectRepresentationConverter.class,
            order = 1)
    private ProjectRepresentation project;

    @CommandLine.Option(names = "--root", description = "Alterna para a raiz do workspace.",
            order = 2)
    private boolean root;

    @Autowired
    private WorkspaceContext workspaceContext;

    @Autowired
    private Console console;

    @Override
    protected void eachOption(String name, Object value) {
        if(name.equals("--projects")) printProjectList();
        if(name.equals("--use")) useProjectOption();
        if(name.equals("--root")) rootOption();
    }

    public void printProjectList() {
        if (listProjectsOption) {
            console.println("total %d", workspaceContext.getWorkspace().getProjects().size());
            workspaceContext.getWorkspace().getProjects().stream()
                    .forEach(p -> console.println("@|blue %s|@ -> %s (%s)", ((ProjectRepresentation) p).getName(), ((ProjectRepresentation) p).getType(), ((ProjectRepresentation) p).getFrameworkVersion()));
            return;
        }
    }

    public void useProjectOption() {
        workspaceContext.setWorkingProject(project);
        console.registerPromptProject(project);
        console.println("Projeto @|bold,blue %s|@ definido com projeto de trabalho do workspace.", workspaceContext.getWorkingProject().getName());
    }

    public void rootOption() {
        workspaceContext.setWorkingProject(null);
        console.registerPromptProject(null);
        console.println("Alterando para a raiz do workspace @|bold,blue %s|@", workspaceContext.getWorkspace().getPath());
    }
}
