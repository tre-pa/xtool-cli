package br.xtool.command.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class ShowProjectsCommand {

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = { "show:projects" }, value = "Exibe os projetos do workspace", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		//		EDirectory directory = EDirectoryImpl.of(this.workspaceService.getWorkspace());
		//		directory.getChildrenDirectories().forEach(System.out::println);
		// @formatter:off
		this.workspaceService.getWorkspace()
			.getProjects()
			.forEach(sb -> System.out.println("Projeto: "+sb.getName()+" version: "+sb.getFrameworkVersion()));
		// @formatter:on
	}
}
