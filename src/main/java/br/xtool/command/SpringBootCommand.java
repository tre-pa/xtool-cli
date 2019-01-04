package br.xtool.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.provider.EntityRepresentationValueProvider;
import br.xtool.core.Workspace;
import br.xtool.core.provider.EJpaRepositoryValueProvider;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.RepositoryRepresentation;
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

	public void genEntities() {

	}

	/**
	 * Comando que gera um novo reposotório.
	 * 
	 * @param entity
	 */
	@ShellMethod(key = "gen:repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void genRepository(@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class) EntityRepresentation entity) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		springBootService.genRepository(springBootProject, entity);
		springBootService.genSpecification(springBootProject, entity);
	}

	/**
	 * Comando que gera uma nova classe de Service.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:service", value = "Gera uma classe Service em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void genService(@ShellOption(help = "Classe de repositório", valueProvider = EJpaRepositoryValueProvider.class) RepositoryRepresentation repository) {
		SpringBootProjectRepresentation bootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		springBootService.genService(bootProject, repository);
	}

	/**
	 * Comando qu gera uma nova classe de Rest.
	 * 
	 * @param repository
	 */
	@ShellMethod(key = "gen:rest", value = "Gera uma classe Rest em um projeto Spring Boot", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void genRest(@ShellOption(help = "Classe de repositório", valueProvider = EJpaRepositoryValueProvider.class) RepositoryRepresentation repository) {
		SpringBootProjectRepresentation bootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		springBootService.genRest(bootProject, repository);
	}

}
