package br.xtool.command.springboot.info;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootRest;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class SpringBootRestInfo extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "info:rest", value = "Exibe informações sobre os Rests do projeto", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		infoAllRests(bootProject);
	}

	private void infoAllRests(EBootProject bootProject) {
		//// @formatter:off
		int maxLenghtEntityName = bootProject.getRests().stream()
				.map(EBootRest::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		bootProject.getRests().stream()
			.forEach(rest -> ConsoleLog.print(ConsoleLog.white(StringUtils.rightPad(rest.getName(), maxLenghtEntityName))));
		// @formatter:on
		ConsoleLog.print(ConsoleLog.yellow(String.valueOf(bootProject.getRests().size())), ConsoleLog.yellow(" classe(s) rest(s) encontrada(s)"));
	}
}
