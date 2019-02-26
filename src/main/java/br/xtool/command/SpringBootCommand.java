package br.xtool.command;

import java.io.IOException;
import java.util.Objects;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Workspace;
import br.xtool.core.provider.EntityRepresentationValueProvider;
import br.xtool.core.provider.NgModuleRepresentationValueProvider;
import br.xtool.core.provider.PlantClassDiagramRepresentationValueProvider;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.service.AngularService;
import br.xtool.service.SpringBootService;
import br.xtool.view.ShowClassDiagramView;

/**
 * Classe com os comandos Shell para Spring Boot.
 * 
 *
 */
@ShellComponent
public class SpringBootCommand {

	@Autowired
	private SpringBootService springBootService;

	@Autowired
	private AngularService angularService;

	@Autowired
	private ApplicationContext appCtx;

	@Autowired
	private Workspace workspace;

	/**
	 * 
	 * @param plantClass
	 */
	@ShellMethod(key = "gen:entities", value = "Gera as classes Jpa do diagrama", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genEntities() {
		SpringBootProjectRepresentation project = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		project.getMainDomainClassDiagram().getClasses().stream().forEach(springBootService::genEntity);
	}

	/**
	 * Comando que gera um novo reposotório.
	 * 
	 * @param entity
	 */
	@ShellMethod(key = "gen:repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genRepository(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class, defaultValue = "") EntityRepresentation entity) {
		if (Objects.isNull(entity)) {
			// @formatter:off
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getEntities().stream()
				.forEach(springBootService::genRepository);
			// @formatter:on
			return;
		}
		springBootService.genRepository(entity);
//		springBootService.genSpecification(entity);
	}

	/**
	 * Comando que gera uma nova classe de Service.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:service", value = "Gera uma classe Service em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genService(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class, defaultValue = "") EntityRepresentation entity) {
		if (Objects.isNull(entity)) {
			// @formatter:off
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getEntities().stream()
				.forEach(springBootService::genService);
			// @formatter:on
			return;
		}
		springBootService.genService(entity);
	}

	/**
	 * Comando qu gera uma nova classe de Rest.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:rest", value = "Gera uma classe Rest em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genRest(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class, defaultValue = "") EntityRepresentation entity) {
		if (Objects.isNull(entity)) {
			// @formatter:off
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getEntities().stream()
				.forEach(springBootService::genRest);
			// @formatter:on
			return;
		}
		springBootService.genRest(entity);
	}

	/**
	 * 
	 * @param plantClass
	 */
	@ShellMethod(key = "gen:ng-entities", value = "Gera as classes Typescript do diagrama no prpojeto Angular", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genNgEntities() {
		SpringBootProjectRepresentation project = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		project.getEntities().stream().forEach(angularService::genNgEntity);
	}

	/**
	 * Comando qu gera uma nova classe de Rest.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:ng-service", value = "Gera uma classe Service Angular", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genNgService(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class, defaultValue = "") EntityRepresentation entity) {
		if (Objects.isNull(entity)) {
			// @formatter:off
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getEntities().stream()
				.forEach(angularService::genNgService);
			// @formatter:on
			return;
		}
		angularService.genNgService(entity);
	}

	@ShellMethod(key = "gen:ng-list", value = "Gera um componente de lista angular para a entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genNgList(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
		this.angularService.genNgList(entity, ngModule);
	}

	@ShellMethod(key = "gen:ng-detail", value = "Gera um componente de detail para a entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genNgDetail(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
		this.angularService.genNgDetail(entity, ngModule);
	}
	
	@ShellMethod(key = "gen:ng-edit", value = "Gera um componente de edit para a entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genNgEdit(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity,
			@ShellOption(help = "Módulo Angular", valueProvider = NgModuleRepresentationValueProvider.class) NgModuleRepresentation ngModule) {
		this.angularService.genNgEdit(entity, ngModule);
	}

	@ShellMethod(key = "show:class-diagram", value = "Exibe o diagrama de classe do projeto", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void showClassDiagram(
			@ShellOption(help = "Nome do arquivo de diagrama do projeto", valueProvider = PlantClassDiagramRepresentationValueProvider.class, defaultValue = "main.md") PlantClassDiagramRepresentation diagram) {
		ShowClassDiagramView guiView = appCtx.getBean(ShowClassDiagramView.class);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				guiView.showDialog(diagram);
			}
		});

	}

	protected Availability availabilitySpringBootCommand() throws IOException {
		return this.workspace.getWorkingProject().getProjectType().equals(ProjectRepresentation.Type.SPRINGBOOT) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}

}
