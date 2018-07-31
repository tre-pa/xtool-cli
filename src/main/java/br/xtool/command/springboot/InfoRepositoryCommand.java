package br.xtool.command.springboot;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootRepository;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class InfoRepositoryCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "info:repository", value = "Exibe informações sobre os Repositórios do projeto", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		infoAllRepositories(bootProject);
	}

	private void infoAllRepositories(EBootProject bootProject) {
		//// @formatter:off
		int maxLenghtEntityName = bootProject.getRepositories().stream()
				.map(EBootRepository::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		bootProject.getRepositories().stream()
			.forEach(repository -> ConsoleLog.print(ConsoleLog.cyan(StringUtils.rightPad(repository.getName(), maxLenghtEntityName)), " - ", ConsoleLog.gray(repository.getPackage().getName())));
		// @formatter:on
	}
}