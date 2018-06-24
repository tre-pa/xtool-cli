package br.xtool.mapper;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.command.SpringBootCommand;

@ShellComponent
public class SpringBootJpaEntityMapper extends SpringBootCommand {

	@ShellMethod(key = "map-springboot-jpa-entity", value = "Mapeia uma entidade JPA existente", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Mapeia a entidade com annotations JPA", defaultValue = "false", arity = 0) Boolean jpa,
			@ShellOption(help = "Mapeia a entidade com annotations Jackson", defaultValue = "false", arity = 0) Boolean jackson) {
	// @formatter:on

	}
}
