package br.xtool.generator.angular;

import java.io.File;
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
import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.service.NgService;

@ShellComponent
public class AngularComponentGenerator extends AngularCommand {

	@Autowired
	private FS fs;

	@Autowired
	private NgService ngService;

	@ShellMethod(key = "gen:ng-component", value = "Gera uma classe component em um projeto Angular", group = XtoolCliApplication.GENERATORS_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe component") String name) throws JDOMException, IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.build();
		// @formatter:on

		ngService.addDeclarationToModule(new ENgModule(new File("/home/jcruz/git/a5-exemple/src/app/app.module.ts")),
				new ENgComponent(new File("/home/jcruz/git/a5-exemple/src/app/view/hello-page/hello-list/hello-list.component.ts")));

		// this.getProject().getNgModules().stream().forEach(System.out::println);
		// System.out.println(this.getProject().getNgPackage());
		// System.out.println(this.getProject().getNgPackage().getDependencies());
	}

}
