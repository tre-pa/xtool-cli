package br.xtool.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Workspace;
import br.xtool.core.provider.EJpaRepositoryValueProvider;
import br.xtool.core.provider.EntityRepresentationValueProvider;
import br.xtool.core.provider.PlantClassRepresentationValueProvider;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.PlantClassRepresentation;
import br.xtool.core.representation.RepositoryRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.service.SpringBootService;

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
	private Workspace workspace;

	/**
	 * Gera um novo projeto Spring Boot.
	 * 
	 * @param name Nome do Projeto Spring Boot
	 */
	@ShellMethod(key = "new:springboot", value = "Novo projeto Spring Boot 1.5.x", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void newApp(@ShellOption(help = "Nome do projeto") String name) {
		springBootService.newApp(name);
	}

	/**
	 * 
	 * @param plantClass
	 */
	@ShellMethod(key = "gen:entity", value = "Gera uma classe Jpa", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void genEntities(
			@ShellOption(value = "--plantClass", help = "Classe do diagrama de classe", valueProvider = PlantClassRepresentationValueProvider.class, defaultValue="") PlantClassRepresentation plantClass) {
		if (Objects.isNull(plantClass)) {
			this.workspace.getWorkingProject(SpringBootProjectRepresentation.class).getDomainClassDiagram().getClasses().stream().forEach(springBootService::genEntity);
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
	public void genRepository(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity) {
		springBootService.genRepository(entity);
		springBootService.genSpecification(entity);
	}

	/**
	 * Comando que gera uma nova classe de Service.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:service", value = "Gera uma classe Service em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void genService(@ShellOption(help = "Classe de repositório", valueProvider = EJpaRepositoryValueProvider.class) RepositoryRepresentation repository) {
		springBootService.genService(repository);
	}

	/**
	 * Comando qu gera uma nova classe de Rest.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:rest", value = "Gera uma classe Rest em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void genRest(@ShellOption(help = "Classe de repositório", valueProvider = EJpaRepositoryValueProvider.class) RepositoryRepresentation repository) {
		springBootService.genRest(repository);
	}

}
