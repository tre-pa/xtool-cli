package br.xtool.core.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.WorkContext;

@ShellComponent
public class PwdCommand {

	@Autowired
	private WorkContext workContext;

	@ShellMethod(value = "Exibe o diretório de trabalho atual", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public String pwd() {
		return workContext.getDirectory().getPath();
	}
}
