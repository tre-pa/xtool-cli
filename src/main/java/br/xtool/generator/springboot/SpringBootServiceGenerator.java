package br.xtool.generator.springboot;

import java.io.IOException;
import java.util.Map;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.FS;
import br.xtool.core.NamePattern;
import br.xtool.core.command.SpringBootCommand;

@ShellComponent
public class SpringBootServiceGenerator extends SpringBootCommand {

	@Autowired
	private FS fs;

	@ShellMethod(key = "gen-springboot-service", value = "Gera uma classe Service", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe Service") String name) throws JDOMException, IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("groupId", this.getProject().getPom().getGroupId())
				.put("serviceName", NamePattern.asServiceClass((name)))
				.build();
		// @formatter:on

		fs.copy("generators/springboot/service/service.java.vm", "src/main/java/${groupId.dir}/service/${serviceName}.java", vars);
	}

}
