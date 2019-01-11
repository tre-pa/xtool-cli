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
import br.xtool.core.provider.PlantClassDiagramRepresentationValueProvider;
import br.xtool.core.provider.PlantClassRepresentationValueProvider;
import br.xtool.core.provider.RepositoryRepresentationValueProvider;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
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
	private ApplicationContext appCtx;

	@Autowired
	private Workspace workspace;

	/**
	 * 
	 * @param plantClass
	 */
	@ShellMethod(key = "gen:entity", value = "Gera uma classe Jpa", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genEntities(
			@ShellOption(value = "--plantClass", help = "Classe do diagrama de classe", valueProvider = PlantClassRepresentationValueProvider.class, defaultValue = "") PlantClassRepresentation plantClass) {
		if (Objects.isNull(plantClass)) {
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getMainDomainClassDiagram().getClasses().stream().forEach(springBootService::genEntity);
			return;
		}
		springBootService.genEntity(plantClass);
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
				.peek(springBootService::genSpecification)
				.forEach(springBootService::genRepository);
			// @formatter:on
			return;
		}
		springBootService.genRepository(entity);
		springBootService.genSpecification(entity);
	}

	/**
	 * Comando que gera uma nova classe de Service.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:service", value = "Gera uma classe Service em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genService(@ShellOption(help = "Classe de repositório", valueProvider = RepositoryRepresentationValueProvider.class, defaultValue = "") RepositoryRepresentation repository) {
		if (Objects.isNull(repository)) {
			// @formatter:off
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getRepositories().stream()
				.forEach(springBootService::genService);
			// @formatter:on
			return;
		}
		springBootService.genService(repository);
	}

	/**
	 * Comando qu gera uma nova classe de Rest.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:rest", value = "Gera uma classe Rest em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	@ShellMethodAvailability("availabilitySpringBootCommand")
	public void genRest(@ShellOption(help = "Classe de repositório", valueProvider = RepositoryRepresentationValueProvider.class, defaultValue = "") RepositoryRepresentation repository) {
		if (Objects.isNull(repository)) {
			// @formatter:off
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getRepositories().stream()
				.forEach(springBootService::genRest);
			// @formatter:on
			return;
		}
		springBootService.genRest(repository);
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
