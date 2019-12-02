package br.xtool.command;

import br.xtool.annotation.CoreCommand;
import br.xtool.annotation.OptionFn;
import br.xtool.command.completer.ProjectRepresentationCompleter;
import br.xtool.command.converter.ProjectRepresentationConverter;
import br.xtool.command.core.AbstractCommand;
import br.xtool.context.WorkspaceContext;
import br.xtool.core.Console;
import br.xtool.representation.ProjectRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Comando para limpar a tela do console.
 */
@CoreCommand
@Command(name = "use", description = "Altera o projeto de trabalho")
public class UseCommand extends AbstractCommand {

	@Autowired
	private Console console;

	@Autowired
    private WorkspaceContext workspaceContext;

    @CommandLine.Option(names = "--project",
            description = "Define o projeto de trabalho",
            completionCandidates = ProjectRepresentationCompleter.class,
            converter = ProjectRepresentationConverter.class,
            order = 1)
    private ProjectRepresentation project;

    @CommandLine.Option(names = "--root", description = "Alterna para a raiz do workspace.",
        order = 2)
    private boolean root;

    @OptionFn("--project")
    public void projectOption() {
        workspaceContext.setWorkingProject(project);
        console.registerPromptProject(project);
    }

    @OptionFn("--root")
    public void rootOption() {
        workspaceContext.setWorkingProject(null);
        console.registerPromptProject(null);
    }

}
