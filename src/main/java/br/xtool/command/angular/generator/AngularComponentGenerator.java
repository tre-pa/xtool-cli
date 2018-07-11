package br.xtool.command.angular.generator;

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
import br.xtool.core.service.NgService;

@ShellComponent
public class AngularComponentGenerator extends AngularCommand {

	@Autowired
	private FS fs;

	@Autowired
	private NgService ngService;

	@ShellMethod(key = "gen:ng-component", value = "Gera uma classe component em um projeto Angular", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Nome da classe component") String name) throws JDOMException, IOException {
	// @formatter:on
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.build();
		// @formatter:on

		// ngService.addDeclarationToModule(ngModule, new ENgDetail(new
		// File("/home/jcruz/git/a5-exemple/src/app/view/hello-page/hello-list/hello-list.component.ts")));

		this.getProject().getNgPages().stream().forEach(System.out::println);
		System.out.println(this.getProject().getNgModules());
		System.out.println(this.getProject().getNgEdits());
		System.out.println(this.getProject().getNgPackage().getDependencies());
	}

}
