package br.xtool.command.angular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.AngularAware;
import br.xtool.core.provider.ENgModuleValueProvider;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgPage;
import br.xtool.core.representation.ENgProject;
import br.xtool.service.NgService;
import br.xtool.service.WorkspaceService;

@Profile("in-dev")
@ShellComponent
public class GenPageCommand extends AngularAware {

	@Autowired
	private NgService ngService;

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "gen:ng-page", value = "Gera uma classe page em um projeto Angular", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Nome da classe page") String name,
			@ShellOption(help= "Nome do módulo", valueProvider=ENgModuleValueProvider.class, defaultValue="AppModule") ENgModule ngModule)  {
	// @formatter:on
		ENgPage ngPage = this.ngService.createNgPage(this.workspaceService.getWorkingProject(ENgProject.class), ngModule, name);
	}

}
