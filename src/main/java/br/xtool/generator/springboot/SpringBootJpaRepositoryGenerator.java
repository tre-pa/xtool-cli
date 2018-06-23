package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.jdom2.JDOMException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.annotation.Template;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.model.Entity;
import br.xtool.core.provider.EntityValueProvider;

/**
 * Comando que gera um classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
@Template(path = "generators/springboot/repository")
public class SpringBootJpaRepositoryGenerator extends SpringBootCommand {

	@ShellMethod(key = "gen-springboot-jpa-repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EntityValueProvider.class) Entity entity) throws IOException, JDOMException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("entity", entity)
				.build();
		// @formatter:on

		this.copyTpl("repository.java.vm", "src/main/java/${groupId.dir}/repository/${entity.name}Repository.java", vars);
	}
}
