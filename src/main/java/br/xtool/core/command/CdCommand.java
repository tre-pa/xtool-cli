package br.xtool.core.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.FileValueProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.WorkContext;

@ShellComponent
public class CdCommand {

	@Autowired
	private WorkContext workContext;

	@ShellMethod(value = "Altera o diretório de trabalho do xtool", group = XtoolCliApplication.CORE_COMMAND_GROUP)
	public void cd(@ShellOption(help = "Diretório alvo", valueProvider = FileValueProvider.class) String dir) {
		workContext.changeTo(dir);
	}
}
