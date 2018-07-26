package br.xtool.command.core;

import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;
import static br.xtool.core.ConsoleLog.white;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.representation.EProject;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class ShowProjectsCommand {

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = { "show:projects" }, value = "Exibe os projetos do workspace", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		this.workspaceService.getWorkspace().getProjects().forEach(this::prettyPrintProject);
	}

	private void prettyPrintProject(EProject project) {
		print(white(project.getName()), " - ", cyan(project.getProjectType().getName()), " ", cyan(project.getFrameworkVersion()));
	}
}
