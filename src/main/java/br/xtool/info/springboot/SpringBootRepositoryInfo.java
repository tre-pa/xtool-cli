package br.xtool.info.springboot;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.ERepository;

@ShellComponent
public class SpringBootRepositoryInfo extends SpringBootCommand {

	@ShellMethod(key = "info:repository", value = "Exibe informações sobre os Repositórios do projeto", group = XtoolCliApplication.INFO_COMMAND_GROUP)
	public void run() {
		infoAllRepositories();
	}

	private void infoAllRepositories() {
		//// @formatter:off
		int maxLenghtEntityName = this.getProject().getRepositories().stream()
				.map(ERepository::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		this.getProject().getRepositories().stream()
			.forEach(repository -> ConsoleLog.print(ConsoleLog.cyan(StringUtils.rightPad(repository.getName(), maxLenghtEntityName)), " - ", ConsoleLog.gray(repository.getPackage().getName())));
		// @formatter:on
	}
}
