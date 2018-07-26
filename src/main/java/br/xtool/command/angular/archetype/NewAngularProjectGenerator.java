package br.xtool.command.angular.archetype;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.RegularAware;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject;
import br.xtool.core.service.FileService;
import br.xtool.core.service.ShellService;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class NewAngularProjectGenerator extends RegularAware {

	@Autowired
	private FileService fs;

	@Autowired
	private ShellService shellService;

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "new:angular", value = "Novo projeto Angular 5.x", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name) throws IOException {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("projectName", name);
			}
		};
		ENgProject project = this.workspaceService.createProject(ENgProject.class, EProject.Type.ANGULAR, name, EProject.Version.V5, vars);
		ConsoleLog.print(ConsoleLog.cyan("\t-- npm install --"));
		this.shellService.runCmd(project.getPath(), "npm i && code .", vars);
	}

}
