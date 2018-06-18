package br.xtool.generator.core;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Log;
import br.xtool.core.PathService;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.generator.GeneratorCommand;

@ShellGeneratorComponent(templatePath = "generators/angular/scaffold/5.x")
public class NewAngularProjectGenerator extends GeneratorCommand {

	@Autowired
	private PathService pathService;

	@Autowired
	private Log log;

	@ShellMethod(key = "new-angular-project", value = "Novo projeto Angular 5.x", group = XtoolCliApplication.CORE_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name) throws IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("projectName", name)
				.build();
		// @formatter:on

		this.setDestinationRoot(name);
		log.print("");

		this.copyTpl("src/app/@core/layout/main-layout/main-layout.component.css.vm", "src/app/@core/layout/main-layout/main-layout.component.css", vars);
		this.copyTpl("src/app/@core/layout/main-layout/main-layout.component.html.vm", "src/app/@core/layout/main-layout/main-layout.component.html", vars);
		this.copyTpl("src/app/@core/layout/main-layout/main-layout.component.ts.vm", "src/app/@core/layout/main-layout/main-layout.component.ts", vars);
		this.copyTpl("src/app/@core/layout/nav-list-layout/nav-list-layout.component.css.vm", "src/app/@core/layout/nav-list-layout/nav-list-layout.component.css", vars);
		this.copyTpl("src/app/@core/layout/nav-list-layout/nav-list-layout.component.html.vm", "src/app/@core/layout/nav-list-layout/nav-list-layout.component.html", vars);
		this.copyTpl("src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts.vm", "src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts", vars);
		this.copyTpl("src/app/@core/layout/nav-view-layout/nav-view-layout.component.css.vm", "src/app/@core/layout/nav-list-layout/nav-view-layout.component.css", vars);
		this.copyTpl("src/app/@core/layout/nav-view-layout/nav-view-layout.component.html.vm", "src/app/@core/layout/nav-list-layout/nav-view-layout.component.html", vars);
		this.copyTpl("src/app/@core/layout/nav-view-layout/nav-view-layout.component.ts.vm", "src/app/@core/layout/nav-list-layout/nav-view-layout.component.ts", vars);
		this.copyTpl("src/app/@core/model/page.ts.vm", "src/app/@core/model/page.ts", vars);
		this.copyTpl("src/app/@core/security/keycloak.js.vm", "src/app/@core/security/keycloak.js", vars);
		this.copyTpl("src/app/@core/security/keycloak.service.ts.vm", "src/app/@core/security/keycloak.service.ts", vars);
		this.copyTpl("src/app/@core/shared/shared.module.ts.vm", "src/app/@core/shared/shared.module.ts", vars);
		this.copyTpl("src/app/@core/util/gitkeep", "src/app/@core/util/.gitkeep", vars);
		this.copyTpl("src/app/domain/gitkeep", "src/app/domain/.gitkeep", vars);
		this.copyTpl("src/app/service/gitkeep", "src/app/service/.gitkeep", vars);
		this.copyTpl("src/app/view/gitkeep", "src/app/view/.gitkeep", vars);
		this.copyTpl("src/app/app-routing.module.ts.vm", "src/app/app-routing.module.ts", vars);
		this.copyTpl("src/app/app.component.css.vm", "src/app/app.component.css", vars);
		this.copyTpl("src/app/app.component.html.vm", "src/app/app.component.html", vars);
		this.copyTpl("src/app/app.component.ts.vm", "src/app/app.component.ts", vars);
		this.copyTpl("src/app/app.module.ts.vm", "src/app/app.module.ts", vars);
		this.copyTpl("src/assets/gitkeep", "src/assets/.gitkeep", vars);
		this.copyTpl("src/environments/environment.dev.ts.vm", "src/environments/environment.dev.ts", vars);
		this.copyTpl("src/environments/environment.prod.ts.vm", "src/environments/environment.prod.ts", vars);
		this.copyTpl("src/environments/environment.ts.vm", "src/environments/environment.ts", vars);
		this.copy("src/favicon.ico", "src/favicon.ico");
		this.copyTpl("src/index.html.vm", "src/index.html", vars);
		this.copyTpl("src/main.ts.vm", "src/main.ts", vars);
		this.copyTpl("src/polyfills.ts.vm", "src/polyfills.ts.vm", vars);
		this.copyTpl("src/polyfills.ts.vm", "src/polyfills.ts.vm", vars);
		this.copyTpl("src/styles.scss.vm", "src/styles.scss", vars);
		this.copyTpl("src/test.ts.vm", "src/test.ts", vars);
		this.copyTpl("src/theme.scss.vm", "src/theme.scss", vars);
		this.copyTpl("src/tsconfig.app.json.vm", "src/tsconfig.app.json", vars);
		this.copyTpl("src/tsconfig.spec.json.vm", "src/tsconfig.spec.json", vars);
		this.copyTpl("src/typings.d.ts.vm", "src/typings.d.ts.json", vars);
		this.copyTpl("angular-cli.json.vm", ".angular-cli.json", vars);
		this.copyTpl("angulardoc.json.vm", ".angulardoc.json", vars);
		this.copyTpl("gitignore.vm", ".gitignore", vars);
		this.copyTpl("gitlab-ci.yml.vm", ".gitlab-ci.yml", vars);
		this.copyTpl("karma.conf.js.vm", "karma.conf.js", vars);
		this.copyTpl("package.json.vm", "package.json", vars);
		this.copyTpl("protractor.conf.js.vm", "protractor.conf.js", vars);
		this.copyTpl("readme.md.vm", "readme.md.js", vars);
		
		this.changeWorkingDirectoryToDestinationRoot();
		this.pathService.exec("npm i");
	}
}
