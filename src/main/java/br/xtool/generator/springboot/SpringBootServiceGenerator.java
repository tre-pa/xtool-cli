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
import strman.Strman;

@ShellComponent
@Template(path = "generators/springboot/service")
public class SpringBootServiceGenerator extends SpringBootCommand {

	@ShellMethod(key = "gen-springboot-service", value = "Gera uma classe Service", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe Service") String name) throws JDOMException, IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("serviceName", this.getFinalName(Strman.toStudlyCase(name)))
				.build();
		// @formatter:on

		this.copyTpl("service.java.vm", "src/main/java/${groupId.dir}/service/${serviceName}.java", vars);
	}

	private String getFinalName(String name) {
		return name.endsWith("Service") ? name : name.concat("Service");
	}
}
