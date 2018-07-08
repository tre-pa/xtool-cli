package br.xtool.generator.angular;

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
import br.xtool.core.command.AngularCommand;

@ShellComponent
public class AngularComponentGenerator extends AngularCommand {

	@Autowired
	private FS fs;

	@ShellMethod(key = "gen:ng-component", value = "Gera uma classe component em um projeto Angular", group = XtoolCliApplication.GENERATORS_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe component") String name) throws JDOMException, IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.build();
		// @formatter:on

		this.getProject().getNgModules().stream().forEach(System.out::println);
		// System.out.println(this.getProject().getNgPackage());
		// System.out.println(this.getProject().getNgPackage().getDependencies());
	}

}
