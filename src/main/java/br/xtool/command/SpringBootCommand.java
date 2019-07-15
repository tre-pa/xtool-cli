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
import br.xtool.command.provider.value.PlantClassDiagramRepresentationValueProvider;
import br.xtool.core.Clog;
import br.xtool.core.Workspace;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.service.SpringBootService;

/**
 * Classe com os comandos Shell para Spring Boot.
 * 
 *
 */
@ShellComponent
public class SpringBootCommand {

	@Autowired
	private SpringBootService springBootProjectService;

	@Autowired
	private Workspace workspace;

	/**
	 * 
	 * @param plantClass
	 */
	@ShellMethod(key = "gen:entities", value = "Gera as classes Jpa do diagrama de classe", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootComma nd")
	public void genEntities(
			@ShellOption(value = "--diagram", help = "Diagrama de classe", valueProvider = PlantClassDiagramRepresentationValueProvider.class, defaultValue = "main.plantuml") PlantClassDiagramRepresentation plantClassDiagram,
			@ShellOption(value = "--verbose", help = "Modo verbose", defaultValue = "false") boolean verbose) {
		Clog.verbose = verbose;
		plantClassDiagram.getClasses().stream().forEach(plantClass -> springBootProjectService.genEntity(workspace.getSpringBootProject().get(), plantClass));
		Clog.verbose = false;
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
			workspace.getSpringBootProject().get().getEntities().stream()
				.forEach(_entity -> springBootProjectService.genRepository(workspace.getSpringBootProject().get(), _entity));
			// @formatter:on
			return;
		}
		springBootProjectService.genRepository(workspace.getSpringBootProject().get(), entity);
		return;
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
			workspace.getSpringBootProject().get().getEntities().stream()
				.forEach(_entity -> springBootProjectService.genService(workspace.getSpringBootProject().get(), _entity));
			// @formatter:on
			return;
		}
		springBootProjectService.genService(workspace.getSpringBootProject().get(), entity);
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
			workspace.getSpringBootProject().get().getEntities().stream()
				.forEach(_entity -> springBootProjectService.genRest(workspace.getSpringBootProject().get(), _entity));
			// @formatter:on
			return;
		}
		springBootProjectService.genRest(workspace.getSpringBootProject().get(), entity);
	}

	@ShellMethod(key = "list:artifacts", value = "Lista os artefatos do projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void listArtifacts(@ShellOption(help = "Entidade JPA", arity = 0, defaultValue = "false") boolean entities,
			@ShellOption(help = "Classes de Repositorio", arity = 0, defaultValue = "false") boolean repositories,
			@ShellOption(help = "Classes de Service", arity = 0, defaultValue = "false") boolean services, 
			@ShellOption(help = "Classes de Rest", arity = 0, defaultValue = "false") boolean rests) {
		SpringBootProjectRepresentation project = workspace.getSpringBootProject().get();
		if (entities) springBootProjectService.printEntities(project);
		if (repositories) springBootProjectService.printRepositories(project);
		if (services) springBootProjectService.printServices(project);
		if (rests) springBootProjectService.printRests(project);
	}

	/*
	 * Define a regra para disponibilidade de comandos.
	 */
	protected Availability availabilitySpringBootCommand() throws IOException {
		String str = "O diretório de trabalho não é um projeto 'Spring Boot' válido. Use o comando 'use' para alterar o projeto de trabalho.";
		return workspace.isSpringBootProject() || workspace.isSpringBootNgProject() ? Availability.available() : Availability.unavailable(str);
	}

}
