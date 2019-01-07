package br.xtool.command;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.service.AngularService;

@ShellComponent
public class AngularCommand {
	
	@Autowired
	private AngularService angularService;
	
	@Autowired
	private Workspace workspace;

	@ShellMethod(key = "new:angular", value = "Novo projeto Angular 5.x", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name, @ShellOption(help = "Qualiifcador de geração", defaultValue="v7-dx") String qualifier) throws IOException {
		angularService.newApp(name, qualifier);
	}
	
	public Availability availabilitySpringBootCommand() throws IOException {
		return this.workspace.getWorkingProject().getProjectType().equals(ProjectRepresentation.Type.ANGULAR) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto angular válido. Use o comando cd para alterar o diretório de trabalho.");
	}
}
