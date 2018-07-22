package br.xtool.command.springboot.info;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EJavaRepository;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.service.ProjectService;

@ShellComponent
public class SpringBootRepositoryInfo extends SpringBootAware {

	@Autowired
	private ProjectService projectService;

	@ShellMethod(key = "info:repository", value = "Exibe informações sobre os Repositórios do projeto", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		EBootProject bootProject = this.projectService.load(EBootProject.class);
		infoAllRepositories(bootProject);
	}

	private void infoAllRepositories(EBootProject bootProject) {
		//// @formatter:off
		int maxLenghtEntityName = bootProject.getRepositories().stream()
				.map(EJavaRepository::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		bootProject.getRepositories().stream()
			.forEach(repository -> ConsoleLog.print(ConsoleLog.cyan(StringUtils.rightPad(repository.getName(), maxLenghtEntityName)), " - ", ConsoleLog.gray(repository.getPackage().getName())));
		// @formatter:on
	}
}
