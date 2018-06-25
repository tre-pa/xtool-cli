package br.xtool.mapper;

import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.Assert;

import br.xtool.XtoolCliApplication;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.model.Entity;
import br.xtool.core.provider.EntityValueProvider;
import br.xtool.mapper.core.JacksonMapper;
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

	/*
	 * Retorna todas as instâncias de classes que impementam LombokMapper
	 */
	@Autowired
	@Lazy
	private Set<LombokMapper> lombokMappers;

	@Autowired
	@Lazy
	private Set<JacksonMapper> jacksonMappers;

	@ShellMethod(key = "map-springboot-jpa-entity", value = "Mapeia uma entidade JPA existente", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Entidade JPA", valueProvider = EntityValueProvider.class, defaultValue="") Entity entity,
			@ShellOption(help = "Todas as entidades JPA", defaultValue = "false", arity = 0) Boolean allEntities,
			@ShellOption(help = "Mapeia a entidade com annotations JPA", defaultValue = "false", arity = 0) Boolean jpa,
			@ShellOption(help = "Mapeia a entidade com annotations Lombok", defaultValue = "false", arity = 0) Boolean lombok,
			@ShellOption(help = "Mapeia a entidade com annotations Jackson", defaultValue = "false", arity = 0) Boolean jackson) {
	// @formatter:on
		Assert.isTrue(Objects.nonNull(entity) || allEntities,
				"Selecione uma entidade ou a opção 'all-entities' para selecionar todas as entidades. Digite 'help map-springboot-jpa-entity' para mais detalhes.");
		Assert.isTrue(jpa || lombok || jackson, "Seleciona pelo menos uma opção de mapeamento. Digite 'help map-springboot-jpa-entity' para mais detalhes.");

		if (Objects.nonNull(entity)) {
			if (jpa) jpaMappers.stream().forEach(mapper -> mapper.apply(entity));
			if (lombok) lombokMappers.stream().forEach(mapper -> mapper.apply(entity));
			if (jackson) jacksonMappers.stream().forEach(mapper -> mapper.apply(entity));
			entity.commitUpdate();
			return;
		}
	}
}
