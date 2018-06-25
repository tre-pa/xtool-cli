package br.xtool.mapper;

import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.model.Entity;
import br.xtool.core.provider.EntityValueProvider;
import br.xtool.mapper.core.JpaMapper;
import br.xtool.mapper.core.LombokMapper;

@ShellComponent
public class SpringBootJpaEntityMapper extends SpringBootCommand {

	/*
	 * Retorna todas as instâncias de classes que impementam JpaMapper.
	 */
	@Autowired
	@Lazy
	private Set<JpaMapper> jpaMappers;

	@Autowired
	@Lazy
	private Set<LombokMapper> lombokMappers;

	@ShellMethod(key = "map-springboot-jpa-entity", value = "Mapeia uma entidade JPA existente", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Entidade JPA", valueProvider = EntityValueProvider.class, defaultValue="") Entity entity,
			@ShellOption(help = "Todas as entidades JPA", defaultValue = "false", arity = 0) Boolean allEntities,
			@ShellOption(help = "Mapeia a entidade com annotations JPA", defaultValue = "false", arity = 0) Boolean jpa,
			@ShellOption(help = "Mapeia a entidade com annotations Lombok", defaultValue = "false", arity = 0) Boolean lombok,
			@ShellOption(help = "Mapeia a entidade com annotations Jackson", defaultValue = "false", arity = 0) Boolean jackson) {
	// @formatter:on
		if (Objects.nonNull(entity)) {
			if (jpa) jpaMappers.stream().forEach(mapper -> mapper.apply(entity));
			if (lombok) lombokMappers.stream().forEach(mapper -> mapper.apply(entity));
			entity.commitUpdate();
			return;
		}
	}
}
