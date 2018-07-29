package br.xtool.command.springboot.map;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.provider.EUmlClassValueProvider;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class MapClassCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "map:class", value = "Mapeia uma classe do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Classe UML", valueProvider = EUmlClassValueProvider.class, defaultValue="", value= {"--class"}) EUmlClass umlClass,
			@ShellOption(help = "Todas as classes UML", defaultValue = "false", arity = 0) Boolean allClasses,
			@ShellOption(help = "Mapeia a entidade com annotations JPA", defaultValue = "false", arity = 0) Boolean jpa,
			@ShellOption(help = "Mapeia a entidade com annotations Lombok", defaultValue = "false", arity = 0) Boolean lombok,
			@ShellOption(help = "Mapeia a entidade com annotations Jackson", defaultValue = "false", arity = 0) Boolean jackson) {
	// @formatter:on
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);

		bootProject.getMainSourceFolder().getPackages().forEach(pkg -> System.out.println("Pacote: " + pkg.getName()));

		//		Assert.isTrue(Objects.nonNull(entity) || allEntities,
		//				"Selecione uma entidade ou a opção 'all-entities' para selecionar todas as entidades. Digite 'help map-springboot-jpa-entity' para mais detalhes.");
		//		Assert.isTrue(jpa || lombok || jackson, "Selecione pelo menos uma opção de mapeamento. Digite 'help map-springboot-jpa-entity' para mais detalhes.");

		if (Objects.nonNull(umlClass)) {
			return;
		}
	}
}
