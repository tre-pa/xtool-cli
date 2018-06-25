package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.jdom2.JDOMException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.NamePattern;
import br.xtool.core.annotation.Template;
import br.xtool.core.command.SpringBootCommand;
import br.xtool.core.model.Repository;
import br.xtool.core.provider.RepositoryValueProvider;
import strman.Strman;

@ShellComponent
@Template(path = "generators/springboot/rest")
public class SpringBootRestGenerator extends SpringBootCommand {

	@ShellMethod(key = "gen-springboot-rest", value = "Gera uma classe Rest", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe Rest") String name) throws JDOMException, IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("restName", NamePattern.asRestClass(name))
				.put("restPath", NamePattern.asRestPath(name))
				.build();
		// @formatter:on

		this.copyTpl("rest.java.vm", "src/main/java/${groupId.dir}/rest/${restName}.java", vars);
	}
}