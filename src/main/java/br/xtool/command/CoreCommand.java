package br.xtool.command;

import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;
import static br.xtool.core.ConsoleLog.white;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Workspace;
import br.xtool.core.provider.ProjectRepresentationValueProvider;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.service.AngularService;
import br.xtool.service.SpringBootService;
import br.xtool.view.ShowClassDiagramView;

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
	private ApplicationContext appCtx;

	@Autowired
	private Workspace workspace;

	/**
	 * Gera uma aplicação Spring Boot e Angular integrada.
	 * 
	 * @param name Nome da aplicação.
	 */
	@ShellMethod(key = "new:app", value = "Novo projeto Spring Boot e Angular", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void newApp(@ShellOption(help = "Nome do projeto") String name, 
			@ShellOption(help = "Versão da aplicação SpringBoot", defaultValue = "v2", value = "--springboot-version") String sbversion,
			@ShellOption(help = "Versão da aplicação Angular", defaultValue = "v7", value = "--angular-version") String ngversion) {
		springBootService.newApp(name, sbversion);
		angularService.newApp(name, ngversion);
	}

	/**
	 * Gera um novo projeto Spring Boot.
	 * 
	 * @param name Nome do Projeto Spring Boot
	 */
	@ShellMethod(key = "new:springboot", value = "Novo projeto Spring Boot v2", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void newAppSringBoot(@ShellOption(help = "Nome do projeto") String name, @ShellOption(help = "Versão da aplicação", defaultValue = "v2") String version) {
		springBootService.newApp(name, version);
	}

	/**
	 * 
	 * @param name
	 * @param qualifier
	 * @throws IOException
	 */
	@ShellMethod(key = "new:angular", value = "Novo projeto Angular 7.x", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void newAppAngular(@ShellOption(help = "Nome do projeto") String name, @ShellOption(help = "Versão da aplicação", defaultValue = "v7") String version) throws IOException {
		angularService.newApp(name, version);
	}

	/**
	 * Exibe os projetos disponíveis no workspace atual.
	 * 
	 */
	@ShellMethod(key = { "list:projects" }, value = "Exibe os projetos do workspace", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
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
