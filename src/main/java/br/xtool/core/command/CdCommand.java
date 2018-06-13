package br.xtool.core.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;

@ShellComponent
public class CdCommand {

	@ShellMethod(value = "Altera o diretório de trabalho do xtool", group = XtoolCliApplication.CORE_COMMAND_GROUP)
	public void cd(@ShellOption(help = "Diretório alvo") String dir) {

	}
}
