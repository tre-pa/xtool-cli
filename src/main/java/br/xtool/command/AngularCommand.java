package br.xtool.command;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Workspace;
import br.xtool.core.provider.NgModuleRepresentationValueProvider;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.service.AngularService;

@ShellComponent
public class AngularCommand {

	@Autowired
	private AngularService angularService;

	@Autowired
	private Workspace workspace;

	@ShellMethod(group = XtoolCliApplication.ANGULAR_COMMAND_GROUP, value = "Teste nos módulos")
	public void showPage(@ShellOption(help = "Móulo Angular", value = "--module", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule
			) {
//		System.out.println(ngModule.getModuleDeclarations());
//		System.out.println(ngModule.getAssociatedPage());
		System.out.println(ngModule.getAssociatedPage().get().getNavigations());
//		ngModule.addImport(ngComponent);
	}

	public Availability availabilityAngularCommand() throws IOException {
		return this.workspace.getWorkingProject().getProjectType().equals(ProjectRepresentation.Type.ANGULAR) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto angular válido. Use o comando cd para alterar o diretório de trabalho.");
	}

}