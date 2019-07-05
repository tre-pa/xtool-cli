package br.xtool.command;

import java.io.IOException;
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
import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.service.AngularProjectService;

@ShellComponent
public class AngularCommand {

	@Autowired
	private AngularProjectService angularService;

	@Autowired
	private Workspace workspace;

	/**
	 *
	 * @param plantClass
	 */
	@ShellMethod(key = "gen:ng-entities", value = "Gera as classes Typescript do diagrama no projeto Angular", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	@ShellMethodAvailability("availabilityAngularCommand")
	public void genNgEntities() {
		SpringBootProjectRepresentation project = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		project.getEntities().stream().forEach(this.angularService::genNgEntity);
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
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getEntities().stream()
			.forEach(this.angularService::genNgService);
			// @formatter:on
			return;
		}
		this.angularService.genNgService(entity);
	}

	// @ShellMethod(key = "gen:ng-list", value = "Gera um componente de lista angular para a entidade JPA", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	// @ShellMethodAvailability("availabilityAngularCommand")
	// public void genNgList(
	//	// @formatter:off
	//			@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
	//			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
	//	// @formatter:on
	// this.angularService.genNgList(entity, ngModule);
	// }

	// @ShellMethod(key = "gen:ng-detail", value = "Gera um componente de detail para a entidade JPA", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	// @ShellMethodAvailability("availabilityAngularCommand")
	// public void genNgDetail(
	//	// @formatter:off
	//			@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
	//			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
	//	// @formatter:on
	// this.angularService.genNgDetail(entity, ngModule);
	// }

	// @ShellMethod(key = "gen:ng-edit", value = "Gera um componente de edit para a entidade JPA", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	// @ShellMethodAvailability("availabilityAngularCommand")
	// public void genNgEdit(
	//	// @formatter:off
	//			@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
	//			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
	//	// @formatter:on
	// this.angularService.genNgEdit(entity, ngModule);
	// }

	@ShellMethod(key = "gen:ng-crud", value = "Gera um componente de crud para a entidade JPA", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	@ShellMethodAvailability("availabilityAngularCommand")
	public void genNgEdit(
	// @formatter:off
			@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
		// @formatter:on
		this.angularService.genNgCrud(entity, ngModule);
	}

	@ShellMethod(key = "list:ng-artifacts", value = "Lista os artefatos do projeto Angular", group = XtoolCliApplication.ANGULAR_COMMAND_GROUP)
	@ShellMethodAvailability("availabilityAngularCommand")
	public void listNgArtifacts(@ShellOption(help = "Entidades de domínio Angular", arity = 0, defaultValue = "false") boolean ngEntities,
			@ShellOption(help = "Componentes de listagem", arity = 0, defaultValue = "false") boolean ngLists) {
		SpringBootProjectRepresentation project = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		if (project.getAssociatedAngularProject().isPresent()) {
			NgProjectRepresentation ngProject = project.getAssociatedAngularProject().get();
			if (ngEntities) this.angularService.printNgEntities(ngProject);
			if (ngLists) this.angularService.printNgLists(ngProject);
		}
	}

	public Availability availabilityAngularCommand() throws IOException {
		return isSpringBootProject() || isSpringBootNgProject() ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não possui um projeto angular associado. Use o comando 'use' para alterar o diretório de trabalho.");
	}

	protected boolean isSpringBootProject() {
		return this.workspace.getWorkingProjectType().equals(ProjectRepresentation.Type.SPRINGBOOT)
				&& this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getAssociatedAngularProject().isPresent();
	}

	protected boolean isSpringBootNgProject() {
		return this.workspace.getWorkingProjectType().equals(ProjectRepresentation.Type.SPRINGBOOTNG);
	}
}