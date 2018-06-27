package br.xtool.generator.core;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.FS;
import br.xtool.core.Log;

@ShellComponent
public class NewAngularProjectGenerator {

	@Autowired
	private FS fs;

	@ShellMethod(key = "new-angular-project", value = "Novo projeto Angular 5.x", group = XtoolCliApplication.PROJECT_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name) throws IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("projectName", name)
				.build();
		// @formatter:on

		Log.print("");

		fs.copy("${templatePath}/src/data/gitkeep", "${projectName}/src/data/.gitkeep", vars);
		fs.copy("${templatePath}/scripts/gitkeep", "${projectName}/scripts/.gitkeep", vars);
		fs.copy("${templatePath}/src/app/@core/layout/main-layout/main-layout.component.css.vm", "${projectName}/src/app/@core/layout/main-layout/main-layout.component.css", vars);
		fs.copy("${templatePath}/src/app/@core/layout/main-layout/main-layout.component.html.vm", "${projectName}/src/app/@core/layout/main-layout/main-layout.component.html", vars);
		fs.copy("${templatePath}/src/app/@core/layout/main-layout/main-layout.component.ts.vm", "${projectName}/src/app/@core/layout/main-layout/main-layout.component.ts", vars);
		fs.copy("${templatePath}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.css.vm", "${projectName};src/app/@core/layout/nav-list-layout/nav-list-layout.component.css", vars);
		fs.copy("${templatePath}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.html.vm", "${projectName}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.html", vars);
		fs.copy("${templatePath}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts.vm", "${projectName}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts", vars);
		fs.copy("${templatePath}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.css.vm", "${projectName}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.css", vars);
		fs.copy("${templatePath}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.html.vm", "${projectName}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.html", vars);
		fs.copy("${templatePath}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.ts.vm", "${projectName}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.ts", vars);
		fs.copy("${templatePath}/src/app/@core/model/page.ts.vm", "${projectName}/src/app/@core/model/page.ts", vars);
		fs.copy("${templatePath}/src/app/@core/security/keycloak.js.vm", "${projectName}/src/app/@core/security/keycloak.js", vars);
		fs.copy("${templatePath}/src/app/@core/security/keycloak.service.ts.vm", "${projectName}/src/app/@core/security/keycloak.service.ts", vars);
		fs.copy("${templatePath}/src/app/@core/shared/shared.module.ts.vm", "${projectName}/src/app/@core/shared/shared.module.ts", vars);
		fs.copy("${templatePath}/src/app/@core/util/gitkeep", "${projectName}/src/app/@core/util/.gitkeep", vars);
		fs.copy("${templatePath}/src/app/domain/gitkeep", "${projectName}/src/app/domain/.gitkeep", vars);
		fs.copy("${templatePath}/src/app/service/gitkeep", "${projectName}/src/app/service/.gitkeep", vars);
		fs.copy("${templatePath}/src/app/view/gitkeep", "${projectName}/src/app/view/.gitkeep", vars);
		fs.copy("${templatePath}/src/app/app-routing.module.ts.vm", "${projectName}/src/app/app-routing.module.ts", vars);
		fs.copy("${templatePath}/src/app/app.component.css.vm", "${projectName}/src/app/app.component.css", vars);
		fs.copy("${templatePath}/src/app/app.component.html.vm", "${projectName}/src/app/app.component.html", vars);
		fs.copy("${templatePath}/src/app/app.component.ts.vm", "${projectName}/src/app/app.component.ts", vars);
		fs.copy("${templatePath}/src/app/app.module.ts.vm", "${projectName}/src/app/app.module.ts", vars);
		fs.copy("${templatePath}/src/assets/gitkeep", "${projectName}/src/assets/.gitkeep", vars);
		fs.copy("${templatePath}/src/environments/environment.dev.ts.vm", "${projectName}/src/environments/environment.dev.ts", vars);
		fs.copy("${templatePath}/src/environments/environment.prod.ts.vm", "${projectName}/src/environments/environment.prod.ts", vars);
		fs.copy("${templatePath}/src/environments/environment.ts.vm", "${projectName}/src/environments/environment.ts", vars);
		fs.copy("${templatePath}/src/favicon.ico", "${projectName}/src/favicon.ico", vars);
		fs.copy("${templatePath}/src/index.html.vm", "${projectName}/src/index.html", vars);
		fs.copy("${templatePath}/src/main.ts.vm", "${projectName}/src/main.ts", vars);
		fs.copy("${templatePath}/src/polyfills.ts.vm", "${projectName}/src/polyfills.ts", vars);
		fs.copy("${templatePath}/src/styles.scss.vm", "${projectName}/src/styles.scss", vars);
		fs.copy("${templatePath}/src/test.ts.vm", "${projectName}/src/test.ts", vars);
		fs.copy("${templatePath}/src/theme.scss.vm", "${projectName}/src/theme.scss", vars);
		fs.copy("${templatePath}/src/tsconfig.app.json.vm", "${projectName}/src/tsconfig.app.json", vars);
		fs.copy("${templatePath}/src/tsconfig.spec.json.vm", "${projectName}/src/tsconfig.spec.json", vars);
		fs.copy("${templatePath}/src/typings.d.ts.vm", "${projectName}/src/typings.d.ts.json", vars);
		fs.copy("${templatePath}/angular-cli.json.vm", "${projectName}/.angular-cli.json", vars);
		fs.copy("${templatePath}/angulardoc.json.vm", "${projectName}/.angulardoc.json", vars);
		fs.copy("${templatePath}/editorconfig.vm", "${projectName}/.editorconfig", vars);
		fs.copy("${templatePath}/gitignore.vm", "${projectName}/.gitignore", vars);
		fs.copy("${templatePath}/gitlab-ci.yml.vm", "${projectName}/.gitlab-ci.yml", vars);
		fs.copy("${templatePath}/karma.conf.js.vm", "${projectName}/karma.conf.js", vars);
		fs.copy("${templatePath}/package.json.vm", "${projectName}/package.json", vars);
		fs.copy("${templatePath}/protractor.conf.js.vm", "${projectName}/protractor.conf.js", vars);
		fs.copy("${templatePath}/readme.md.vm", "${projectName}/readme.md.js", vars);

		Log.print("");

		// this.pathService.exec("npm i && code .");
	}

}
