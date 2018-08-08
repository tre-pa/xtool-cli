package br.xtool.command.springboot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.RegularAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EProject;
import br.xtool.core.support.BootProjectJpaSupport;
import br.xtool.service.BootService;
import br.xtool.service.WorkspaceService;

/**
 * Shell Commando responsável por criar uma projeto Spring Boot 1.5.x
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class NewSpringBootProjectGenerator extends RegularAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@ShellMethod(key = "new:springboot", value = "Novo projeto Spring Boot 1.5.x", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Nome do projeto") String name, 
//			@ShellOption(help = "Versão do projeto", defaultValue = "0.0.1") String version,
			@ShellOption(help = "Desativa a dependência jpa", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desativa a dependência web", defaultValue = "false", arity = 0) boolean noWeb) throws IOException {
	// @formatter:on
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("projectName", EBootProject.genProjectName(name));
				put("rootPackage", EBootProject.genRootPackage(name));
				put("baseClassName", EBootProject.genBaseClassName(name));
			}
		};
		EBootProject bootProject = this.workspaceService.createProject(EBootProject.class, EProject.Type.SPRINGBOOT, EBootProject.genProjectName(name), EProject.Version.V1, vars);
		if (!noJpa) this.bootService.addSupport(bootProject, BootProjectJpaSupport.class);
	}

}
