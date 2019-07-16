package br.xtool.command;

import java.io.IOException;
import java.sql.Clob;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.command.provider.value.EntityRepresentationValueProvider;
import br.xtool.command.provider.value.NgModuleRepresentationValueProvider;
import br.xtool.core.Clog;
import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.service.AngularService;


@ShellComponent
public class AngularCommand {

	@Autowired
	private AngularService angularService;

	@Autowired
	private Workspace workspace;

	/**
	 *
	 * @param plantClass
	 */
	@ShellMethod(key = "gen:ng-entities", value = "Gera as classes Typescript do diagrama no projeto Angular", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	@ShellMethodAvailability("availabilityAngularCommand")
	public void genNgEntities() {
		// @formatter:off
		workspace.getAngularProject().get().getTargetSpringBootProject()
			.getEntities()
			.stream()
			.forEach(_entity -> angularService.genNgEntity(workspace.getAngularProject().get(), _entity));
		// @formatter:on
	}

	/**
	 * Comando qu gera uma nova classe de Rest.
	 *
	 * @param repository
	 */
	@ShellMethod(key = "gen:ng-service", value = "Gera uma classe Service Angular", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	@ShellMethodAvailability("availabilityAngularCommand")
	public void genNgService(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class, defaultValue = "") EntityRepresentation entity) {
		if (Objects.isNull(entity)) {
			// @formatter:off
			workspace.getAngularProject().get().getTargetSpringBootProject().getEntities()
				.stream()
				.forEach(_entity -> angularService.genNgService(workspace.getAngularProject().get(), _entity));
			// @formatter:on
			return;
		}
		angularService.genNgService(workspace.getAngularProject().get(), entity);
	}

	@ShellMethod(key = "gen:ng-crud", value = "Gera um componente de crud para a entidade JPA", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	@ShellMethodAvailability("availabilityAngularCommand")
	public void genNgEdit(
	// @formatter:off
			@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
		// @formatter:on
		angularService.genNgCrud(workspace.getAngularProject().get(), entity, ngModule);
	}

	@ShellMethod(key = "list:ng-artifacts", value = "Lista os artefatos do projeto Angular", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	@ShellMethodAvailability("availabilityAngularCommand")
	public void listNgArtifacts(@ShellOption(help = "Entidades de domínio Angular", arity = 0, defaultValue = "false") boolean ngEntities,
			@ShellOption(help = "Componentes de listagem", arity = 0, defaultValue = "false") boolean ngLists) {
		SpringBootProjectRepresentation project = workspace.getSpringBootProject().get();
		if (project.getAssociatedAngularProject().isPresent()) {
			NgProjectRepresentation ngProject = project.getAssociatedAngularProject().get();
			if (ngEntities) angularService.printNgEntities(ngProject);
			if (ngLists) angularService.printNgLists(ngProject);
		}
	}

	public Availability availabilityAngularCommand() throws IOException {
		return isSpringBootProject() || isSpringBootNgProject() ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não possui um projeto angular associado. Use o comando 'use' para alterar o diretório de trabalho.");
	}

	protected boolean isSpringBootProject() {
		return workspace.getWorkingProjectType().equals(ProjectRepresentation.Type.SPRINGBOOT)
				&& workspace.getWorkingProject(SpringBootProjectRepresentation.class).getAssociatedAngularProject().isPresent();
	}

	protected boolean isSpringBootNgProject() {
		return workspace.getWorkingProjectType().equals(ProjectRepresentation.Type.SPRINGBOOTNG);
	}
}