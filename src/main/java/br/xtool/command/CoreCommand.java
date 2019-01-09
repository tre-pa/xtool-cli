package br.xtool.command;

import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;
import static br.xtool.core.ConsoleLog.white;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Workspace;
import br.xtool.core.provider.ProjectRepresentationValueProvider;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.NgProjectRepresentation;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.service.AngularService;
import br.xtool.service.SpringBootService;

/**
 * Classe com os comandos padrões.
 *
 *
 */
@ShellComponent
public class CoreCommand {

	@Autowired
	private SpringBootService springBootService;

	@Autowired
	private AngularService angularService;

	@Autowired
	private Workspace workspace;

	/**
	 * Gera uma aplicação Spring Boot e Angular integrada.
	 * 
	 * @param name Nome da aplicação.
	 */
	@ShellMethod(key = "new:app", value = "Novo projeto Spring Boot e Angular", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void newApp(@ShellOption(help = "Nome do projeto") String name,
			@ShellOption(help = "Qualificador do projeto Angular", defaultValue="v7-dx") String ngQualifier,
			@ShellOption(help = "Qualiifcador do projeto Spring Boot", defaultValue="v2") String qualifier) {
		springBootService.newApp(name, qualifier);
		angularService.newApp(name, ngQualifier);
	}

	/**
	 * Exibe os projetos disponíveis no workspace atual.
	 * 
	 */
	@ShellMethod(key = { "show:projects" }, value = "Exibe os projetos do workspace", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		Consumer<ProjectRepresentation> prettyPrintProject = (project) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(white(project.getName()));
			sb.append(" : ");
			sb.append(cyan(project.getProjectType().getName()));
			sb.append("-");
			sb.append(cyan(project.getProjectVersion().getName()));
			if (project instanceof SpringBootProjectRepresentation) {
				Optional<NgProjectRepresentation> ngProject = SpringBootProjectRepresentation.class.cast(project).getAssociatedAngularProject();
				if (ngProject.isPresent()) {
					sb.append(" -> ");
					sb.append(ngProject.get().getName());
				}
			}
			print(sb.toString());
		};
		this.workspace.getWorkspace().getProjects().forEach(prettyPrintProject);
	}

	/**
	 * Define o diretório de trabalho no workspace.
	 * 
	 * @param project projeto do workspace.
	 */
	@ShellMethod(value = "Define o projeto de trabalho atual", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void use(@ShellOption(help = "Nome do projeto do workspace", valueProvider = ProjectRepresentationValueProvider.class) ProjectRepresentation project) {
		this.workspace.setWorkingProject(project);
	}

}
