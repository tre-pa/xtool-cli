package br.xtool.mapper;

import java.util.Objects;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.Assert;

import br.xtool.XtoolCliApplication;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.provider.EntityRepresentationValueProvider;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.updater.AddAnnotationStringValue;
import br.xtool.core.representation.updater.AddEntityAnnotation;
import br.xtool.core.representation.updater.AddImport;

@ShellComponent
public class SpringBootJpaEntityMapper extends SpringBootCommand {

	@ShellMethod(key = "map-springboot-jpa-entity", value = "Mapeia uma entidade JPA existente", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Entidade JPA", valueProvider = EntityRepresentationValueProvider.class, defaultValue="") EntityRepresentation entity,
			@ShellOption(help = "Todas as entidades JPA", defaultValue = "false", arity = 0) Boolean allEntities,
			@ShellOption(help = "Mapeia a entidade com annotations JPA", defaultValue = "false", arity = 0) Boolean jpa,
			@ShellOption(help = "Mapeia a entidade com annotations Lombok", defaultValue = "false", arity = 0) Boolean lombok,
			@ShellOption(help = "Mapeia a entidade com annotations Jackson", defaultValue = "false", arity = 0) Boolean jackson) {
	// @formatter:on
		Assert.isTrue(Objects.nonNull(entity) || allEntities,
				"Selecione uma entidade ou a opção 'all-entities' para selecionar todas as entidades. Digite 'help map-springboot-jpa-entity' para mais detalhes.");
		Assert.isTrue(jpa || lombok || jackson, "Selecione pelo menos uma opção de mapeamento. Digite 'help map-springboot-jpa-entity' para mais detalhes.");

		if (Objects.nonNull(entity)) {
			entity.addUpdate(requests -> {
				//requests.add(AddImport.of(""));
			});
			// entity.addUpdate(AddImport.of("br.jus.tre_pa"));
			// entity.addUpdate(AddEntityAnnotation.of("lombok.Getter"));
			// entity.addUpdate(AddEntityAnnotation.of("lombok.EqualsAndHashCode",
			// AddAnnotationStringValue.of("of", new String[] { "id", "name" })));
			// entity.commitUpdates();
			return;
		}
	}
}
