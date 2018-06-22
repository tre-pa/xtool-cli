package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.jdom2.JDOMException;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.generator.SpringBootGeneratorCommand;
import strman.Strman;

@ShellGeneratorComponent(templatePath = "generators/springboot/service")
public class SpringBootServiceGenerator extends SpringBootGeneratorCommand {

	@ShellMethod(key = "gen-springboot-service", value = "Gera uma classe Service", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe Service") String name) throws JDOMException, IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("groupIdAsDir", this.getProject().getPom().getGroupAsDir())
				.put("name", this.getFinalName(Strman.toStudlyCase("name")))
				.build();
		// @formatter:on

		this.copyTpl("service.java.vm", "src/main/java/${groupIdAsDir}/service/${name}.java", vars);
	}

	private String getFinalName(String name) {
		return name.endsWith("Service") ? name : name.concat("Service");
	}
}
