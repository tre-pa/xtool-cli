package br.xtool.command.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.representation.impl.EDirectoryImpl;
import br.xtool.core.representation.impl.EWorkspaceImpl;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
@Deprecated
public class PwdCommand {

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(value = "Exibe o diretório de trabalho atual", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public String pwd() {
		//		EDirectory directory = EDirectoryImpl.of(this.workspaceService.getWorkspace());
		//		directory.getChildrenDirectories().forEach(System.out::println);
		// @formatter:off
		new EWorkspaceImpl(EDirectoryImpl.of(this.workspaceService.getWorkspace()))
			.getSpringBootProjects()
			.forEach(sb -> System.out.println("Projeto: "+sb.getName()));
		// @formatter:on
		return this.workspaceService.getWorkspace();

	}
}
