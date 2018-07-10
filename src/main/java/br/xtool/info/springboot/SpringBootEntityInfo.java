package br.xtool.info.springboot;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.EEntity;

@ShellComponent
public class SpringBootEntityInfo extends SpringBootCommand {

	@ShellMethod(key = "info:entity", value = "Exibe informações sobre as entidades JPA do projeto", group = XtoolCliApplication.INFO_COMMAND_GROUP)
	public void run() {
		infoAllEntities();
	}

	private void infoAllEntities() {
		//// @formatter:off
		int maxLenghtEntityName = this.getProject().getEntities().stream()
				.map(EEntity::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		this.getProject().getEntities().stream()
			.forEach(entity -> ConsoleLog.print(ConsoleLog.cyan(StringUtils.rightPad(entity.getName(), maxLenghtEntityName)), " - ", ConsoleLog.gray(entity.getPackage().getName())));
		// @formatter:on
	}
}
