package br.xtool.command.springboot.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.service.BootService;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class MapClassesCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@ShellMethod(key = "map:classes", value = "Mapeia uma classe do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Mapeia a entidade com annotations JPA", defaultValue = "false", arity = 0) Boolean jpa,
			@ShellOption(help = "Mapeia a entidade com annotations Lombok", defaultValue = "false", arity = 0) Boolean lombok,
			@ShellOption(help = "Mapeia a entidade com annotations Jackson", defaultValue = "false", arity = 0) Boolean jackson) {
	// @formatter:on
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);

	}

}
