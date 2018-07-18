package br.xtool.command.springboot.info;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.EJavaRest;

@ShellComponent
public class SpringBootRestInfo extends SpringBootCommand {

	@ShellMethod(key = "info:rest", value = "Exibe informações sobre os Rests do projeto", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		infoAllRests();
	}

	private void infoAllRests() {
		//// @formatter:off
		int maxLenghtEntityName = this.getProject().getRests().stream()
				.map(EJavaRest::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		this.getProject().getRests().stream()
			.forEach(rest -> ConsoleLog.print(ConsoleLog.white(StringUtils.rightPad(rest.getName(), maxLenghtEntityName))));
		// @formatter:on
		ConsoleLog.print(ConsoleLog.yellow(String.valueOf(this.getProject().getRests().size())), ConsoleLog.yellow(" classe(s) rest(s) encontrada(s)"));
	}
}
