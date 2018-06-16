package br.xtool.generator.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Log;
import br.xtool.core.PathContext;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.generator.GeneratorCommand;

@ShellGeneratorComponent(templatePath = "generators/angular/scaffold/5.x")
public class NewAngularProjectGenerator extends GeneratorCommand {

	@Autowired
	private PathContext pathCtx;

	@Autowired
	private Log log;
	
	@ShellMethod(key = "new-angular-project", value = "Novo projeto Angular 5.x", group = XtoolCliApplication.CORE_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name) {

	}
}
