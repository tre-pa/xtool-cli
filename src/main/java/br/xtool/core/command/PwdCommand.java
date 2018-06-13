package br.xtool.core.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.PathContext;

@ShellComponent
public class PwdCommand {

	@Autowired
	private PathContext pathCtx;

	@ShellMethod(value = "Exibe o diretório de trabalho atual", group = XtoolCliApplication.CORE_COMMAND_GROUP)
	public String pwd() {
		return pathCtx.getWorkingDirectory();
	}
}
