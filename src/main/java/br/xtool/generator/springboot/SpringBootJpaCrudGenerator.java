package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.model.Entity;
import br.xtool.core.provider.EntityValueProvider;

/**
 * Comando que gera um classe CRUD no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellGeneratorComponent(templatePath = "generators/springboot/crud")
public class SpringBootJpaCrudGenerator extends SpringBootCommand {

	@ShellMethod(key = "gen-springboot-jpa-crud", value = "Gera as classes de CRUD (JpaRepository, Service e Rest) para entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EntityValueProvider.class) Entity entity) throws IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("entity", entity)
				.build();
		// @formatter:on

		this.copyTpl("repository.java.vm", "src/main/java/${entity.parentPackageDir}/repository/${entity.name}Repository.java", vars);
	}
}
