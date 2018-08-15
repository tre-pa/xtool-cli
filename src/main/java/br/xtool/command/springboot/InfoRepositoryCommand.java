package br.xtool.command.springboot;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.service.WorkspaceService;

@Profile("in-dev")
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
				.map(EJpaRepository::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		bootProject.getRepositories().stream()
			.forEach(repository -> ConsoleLog.print(ConsoleLog.cyan(StringUtils.rightPad(repository.getName(), maxLenghtEntityName)), " - ", ConsoleLog.gray(repository.getJavaPackage().getName())));
		// @formatter:on
	}
}
