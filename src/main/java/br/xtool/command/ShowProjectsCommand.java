package br.xtool.command;

import static br.xtool.core.ConsoleLog.cyan;
import static br.xtool.core.ConsoleLog.print;
import static br.xtool.core.ConsoleLog.white;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.ENgProject;
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
		StringBuilder sb = new StringBuilder();
		sb.append(white(project.getName()));
		sb.append(" : ");
		sb.append(cyan(project.getProjectType().getName()));
		sb.append("-");
		sb.append(cyan(project.getProjectVersion().getName()));
		if (project instanceof EBootProject) {
			Optional<ENgProject> ngProject = EBootProject.class.cast(project).getAssociatedAngularProject();
			if (ngProject.isPresent()) {
				sb.append(" -> ");
				sb.append(ngProject.get().getName());
			}
		}
		print(sb.toString());
	}
}
