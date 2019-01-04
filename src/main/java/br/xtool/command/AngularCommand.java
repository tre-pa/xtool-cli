package br.xtool.command;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.service.AngularService;

@ShellComponent
public class AngularCommand {
	
	@Autowired
	private AngularService angularService;

	// FIXME Remover o qualifier
	@ShellMethod(key = "new:angular", value = "Novo projeto Angular 5.x", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name) throws IOException {
		angularService.newApp(name);
	}
}
