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
import br.xtool.core.command.XCommand;

@ShellGeneratorComponent(templatePath = "generators/angular/5.x")
public class NewAngularProjectGenerator extends XCommand {

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

		createScaffoldAngularProject(vars);

		this.changeWorkingDirectoryToDestinationRoot();
		this.pathService.exec("npm i && code .");
	}

	/**
	 * Cria o esqueleto de um projeto Angular 5.x
	 * 
	 * @param vars
	 * @throws IOException
	 */
	private void createScaffoldAngularProject(Map<String, Object> vars) throws IOException {
		this.copy("scaffold/src/data/gitkeep", "src/data/.gitkeep");
		this.copy("scaffold/scripts/gitkeep", "scripts/.gitkeep");
		this.copyTpl("scaffold/src/app/@core/layout/main-layout/main-layout.component.css.vm", "src/app/@core/layout/main-layout/main-layout.component.css", vars);
		this.copyTpl("scaffold/src/app/@core/layout/main-layout/main-layout.component.html.vm", "src/app/@core/layout/main-layout/main-layout.component.html", vars);
		this.copyTpl("scaffold/src/app/@core/layout/main-layout/main-layout.component.ts.vm", "src/app/@core/layout/main-layout/main-layout.component.ts", vars);
		this.copyTpl("scaffold/src/app/@core/layout/nav-list-layout/nav-list-layout.component.css.vm", "src/app/@core/layout/nav-list-layout/nav-list-layout.component.css", vars);
		this.copyTpl("scaffold/src/app/@core/layout/nav-list-layout/nav-list-layout.component.html.vm", "src/app/@core/layout/nav-list-layout/nav-list-layout.component.html", vars);
		this.copyTpl("scaffold/src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts.vm", "src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts", vars);
		this.copyTpl("scaffold/src/app/@core/layout/nav-view-layout/nav-view-layout.component.css.vm", "src/app/@core/layout/nav-view-layout/nav-view-layout.component.css", vars);
		this.copyTpl("scaffold/src/app/@core/layout/nav-view-layout/nav-view-layout.component.html.vm", "src/app/@core/layout/nav-view-layout/nav-view-layout.component.html", vars);
		this.copyTpl("scaffold/src/app/@core/layout/nav-view-layout/nav-view-layout.component.ts.vm", "src/app/@core/layout/nav-view-layout/nav-view-layout.component.ts", vars);
		this.copyTpl("scaffold/src/app/@core/model/page.ts.vm", "src/app/@core/model/page.ts", vars);
		this.copyTpl("scaffold/src/app/@core/security/keycloak.js.vm", "src/app/@core/security/keycloak.js", vars);
		this.copyTpl("scaffold/src/app/@core/security/keycloak.service.ts.vm", "src/app/@core/security/keycloak.service.ts", vars);
		this.copyTpl("scaffold/src/app/@core/shared/shared.module.ts.vm", "src/app/@core/shared/shared.module.ts", vars);
		this.copyTpl("scaffold/src/app/@core/util/gitkeep", "src/app/@core/util/.gitkeep", vars);
		this.copyTpl("scaffold/src/app/domain/gitkeep", "src/app/domain/.gitkeep", vars);
		this.copyTpl("scaffold/src/app/service/gitkeep", "src/app/service/.gitkeep", vars);
		this.copyTpl("scaffold/src/app/view/gitkeep", "src/app/view/.gitkeep", vars);
		this.copyTpl("scaffold/src/app/app-routing.module.ts.vm", "src/app/app-routing.module.ts", vars);
		this.copyTpl("scaffold/src/app/app.component.css.vm", "src/app/app.component.css", vars);
		this.copyTpl("scaffold/src/app/app.component.html.vm", "src/app/app.component.html", vars);
		this.copyTpl("scaffold/src/app/app.component.ts.vm", "src/app/app.component.ts", vars);
		this.copyTpl("scaffold/src/app/app.module.ts.vm", "src/app/app.module.ts", vars);
		this.copyTpl("scaffold/src/assets/gitkeep", "src/assets/.gitkeep", vars);
		this.copyTpl("scaffold/src/environments/environment.dev.ts.vm", "src/environments/environment.dev.ts", vars);
		this.copyTpl("scaffold/src/environments/environment.prod.ts.vm", "src/environments/environment.prod.ts", vars);
		this.copyTpl("scaffold/src/environments/environment.ts.vm", "src/environments/environment.ts", vars);
		this.copy("scaffold/src/favicon.ico", "src/favicon.ico");
		this.copyTpl("scaffold/src/index.html.vm", "src/index.html", vars);
		this.copyTpl("scaffold/src/main.ts.vm", "src/main.ts", vars);
		this.copyTpl("scaffold/src/polyfills.ts.vm", "src/polyfills.ts", vars);
		this.copyTpl("scaffold/src/styles.scss.vm", "src/styles.scss", vars);
		this.copyTpl("scaffold/src/test.ts.vm", "src/test.ts", vars);
		this.copyTpl("scaffold/src/theme.scss.vm", "src/theme.scss", vars);
		this.copyTpl("scaffold/src/tsconfig.app.json.vm", "src/tsconfig.app.json", vars);
		this.copyTpl("scaffold/src/tsconfig.spec.json.vm", "src/tsconfig.spec.json", vars);
		this.copyTpl("scaffold/src/typings.d.ts.vm", "src/typings.d.ts.json", vars);
		this.copyTpl("scaffold/angular-cli.json.vm", ".angular-cli.json", vars);
		this.copyTpl("scaffold/angulardoc.json.vm", ".angulardoc.json", vars);
		this.copyTpl("scaffold/editorconfig.vm", ".editorconfig", vars);
		this.copyTpl("scaffold/gitignore.vm", ".gitignore", vars);
		this.copyTpl("scaffold/gitlab-ci.yml.vm", ".gitlab-ci.yml", vars);
		this.copyTpl("scaffold/karma.conf.js.vm", "karma.conf.js", vars);
		this.copyTpl("scaffold/package.json.vm", "package.json", vars);
		this.copyTpl("scaffold/protractor.conf.js.vm", "protractor.conf.js", vars);
		this.copyTpl("scaffold/readme.md.vm", "readme.md.js", vars);
	}
}
