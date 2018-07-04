package br.xtool.mapper;

import java.util.Arrays;
import java.util.Objects;

import org.jboss.forge.roaster.model.util.Types;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.Assert;

import br.xtool.XtoolCliApplication;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.provider.EEntityValueProvider;

@ShellComponent
public class SpringBootJpaEntityMapper extends SpringBootCommand {

	@ShellMethod(key = "map-springboot-jpa-entity", value = "Mapeia uma entidade JPA existente", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Entidade JPA", valueProvider = EEntityValueProvider.class, defaultValue="") EEntity entity,
			@ShellOption(help = "Todas as entidades JPA", defaultValue = "false", arity = 0) Boolean allEntities,
			@ShellOption(help = "Mapeia a entidade com annotations JPA", defaultValue = "false", arity = 0) Boolean jpa,
			@ShellOption(help = "Mapeia a entidade com annotations Lombok", defaultValue = "false", arity = 0) Boolean lombok,
			@ShellOption(help = "Mapeia a entidade com annotations Jackson", defaultValue = "false", arity = 0) Boolean jackson) {
	// @formatter:on
		Assert.isTrue(Objects.nonNull(entity) || allEntities,
				"Selecione uma entidade ou a opção 'all-entities' para selecionar todas as entidades. Digite 'help map-springboot-jpa-entity' para mais detalhes.");
		Assert.isTrue(jpa || lombok || jackson, "Selecione pelo menos uma opção de mapeamento. Digite 'help map-springboot-jpa-entity' para mais detalhes.");
		
		System.out.println(Types.getPackage("lombok.Getter"));
		System.out.println(Types.toSimpleName("lombok.Getter"));
		System.out.println(Arrays.deepToString(Types.splitGenerics("Map<String, Long>")));
		
		if (Objects.nonNull(entity)) {
			//entity.addAnnotation("foo.Abc");
			//entity.save();
			// entity.addUpdate(requests -> {
			// requests.add(AddImport.of(""));
			// });
			// entity.addUpdate(AddImport.of("br.jus.tre_pa"));
			// entity.addUpdate(AddEntityAnnotation.of("lombok.Getter"));
			// entity.addUpdate(AddEntityAnnotation.of("lombok.EqualsAndHashCode",
			// AddAnnotationStringValue.of("of", new String[] { "id", "name" })));
			// entity.commitUpdates();
			return;
		}
	}
}
