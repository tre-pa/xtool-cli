package br.xtool.command.angular.generator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.AngularAware;
import br.xtool.core.provider.ENgModuleValueProvider;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.service.FileService;
import br.xtool.core.service.NgService;

@ShellComponent
public class AngularPageGenerator extends AngularAware {

	@Autowired
	private FileService fs;

	@Autowired
	private NgService ngService;

	@ShellMethod(key = "gen:ng-page", value = "Gera uma classe page em um projeto Angular", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Nome da classe page") String name,
			@ShellOption(help= "Nome do módulo", valueProvider=ENgModuleValueProvider.class, defaultValue="AppModule") ENgModule ngModule)  {
	// @formatter:on
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		// @formatter:on

		//		this.ngService.addDeclarationToModule(ngModule, new ENgDetail(new File("/home/jcruz/git/a5-exemple/src/app/view/hello-page/hello-list/hello-list.component.ts")));

	}

}
