package br.xtool.command.angular.archetype;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.CommandLineExecutor;
import br.xtool.core.ConsoleLog;
import br.xtool.core.FS;
import br.xtool.core.WorkContext;
import br.xtool.core.command.RegularCommand;

@ShellComponent
public class NewAngularProjectGenerator extends RegularCommand {

	@Autowired
	private FS fs;

	@Autowired
	private CommandLineExecutor executor;

	@Autowired
	private WorkContext workContext;

	@ShellMethod(key = "new:angular", value = "Novo projeto Angular 5.x", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name) throws IOException {
		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "angular/5.x/archetype")
				.put("projectName", name)
				.put("projectPath", FilenameUtils.concat(this.workContext.getDirectory().getPath(), name))
				.build();
		// @formatter:on

		ConsoleLog.print("");

		this.fs.createEmptyPath("${projectName}/data", vars);
		this.fs.createEmptyPath("${projectName}/scripts", vars);
		this.fs.createEmptyPath("${projectName}/src/app/service", vars);
		this.fs.createEmptyPath("${projectName}/src/app/domain", vars);
		this.fs.createEmptyPath("${projectName}/src/assets", vars);
		this.fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "${projectName}/scripts/xtool-ng.js", vars);
		this.fs.copy("${templatePath}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.html.vm", "${projectName}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.html",
				vars);
		this.fs.copy("${templatePath}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts.vm", "${projectName}/src/app/@core/layout/nav-list-layout/nav-list-layout.component.ts", vars);
		this.fs.copy("${templatePath}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.html.vm", "${projectName}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.html",
				vars);
		this.fs.copy("${templatePath}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.ts.vm", "${projectName}/src/app/@core/layout/nav-view-layout/nav-view-layout.component.ts", vars);
		this.fs.copy("${templatePath}/src/app/@core/security/keycloak.js.vm", "${projectName}/src/app/@core/security/keycloak.js", vars);
		this.fs.copy("${templatePath}/src/app/@core/security/keycloak.service.ts.vm", "${projectName}/src/app/@core/security/keycloak.service.ts", vars);
		this.fs.copy("${templatePath}/src/app/@core/shared.module.ts.vm", "${projectName}/src/app/@core/shared.module.ts", vars);
		this.fs.copy("${templatePath}/src/app/@core/page.ts.vm", "${projectName}/src/app/@core/page.ts", vars);
		this.fs.copy("${templatePath}/src/app/view/@common/nav-link/nav-link.component.html.vm", "${projectName}/src/app/view/@common/nav-link/nav-link.component.html", vars);
		this.fs.copy("${templatePath}/src/app/view/@common/nav-link/nav-link.component.ts.vm", "${projectName}/src/app/view/@common/nav-link/nav-link.component.ts", vars);
		this.fs.copy("${templatePath}/src/app/app-routing.module.ts.vm", "${projectName}/src/app/app-routing.module.ts", vars);
		this.fs.copy("${templatePath}/src/app/app.component.css.vm", "${projectName}/src/app/app.component.css", vars);
		this.fs.copy("${templatePath}/src/app/app.component.html.vm", "${projectName}/src/app/app.component.html", vars);
		this.fs.copy("${templatePath}/src/app/app.component.ts.vm", "${projectName}/src/app/app.component.ts", vars);
		this.fs.copy("${templatePath}/src/app/app.module.ts.vm", "${projectName}/src/app/app.module.ts", vars);
		this.fs.copy("${templatePath}/src/environments/environment.dev.ts.vm", "${projectName}/src/environments/environment.dev.ts", vars);
		this.fs.copy("${templatePath}/src/environments/environment.prod.ts.vm", "${projectName}/src/environments/environment.prod.ts", vars);
		this.fs.copy("${templatePath}/src/environments/environment.ts.vm", "${projectName}/src/environments/environment.ts", vars);
		this.fs.copy("${templatePath}/src/favicon.ico", "${projectName}/src/favicon.ico", vars, true);
		this.fs.copy("${templatePath}/src/index.html.vm", "${projectName}/src/index.html", vars);
		this.fs.copy("${templatePath}/src/main.ts.vm", "${projectName}/src/main.ts", vars);
		this.fs.copy("${templatePath}/src/polyfills.ts.vm", "${projectName}/src/polyfills.ts", vars);
		this.fs.copy("${templatePath}/src/styles.scss.vm", "${projectName}/src/styles.scss", vars);
		this.fs.copy("${templatePath}/src/test.ts.vm", "${projectName}/src/test.ts", vars);
		this.fs.copy("${templatePath}/src/theme.scss.vm", "${projectName}/src/theme.scss", vars);
		this.fs.copy("${templatePath}/src/tsconfig.app.json.vm", "${projectName}/src/tsconfig.app.json", vars);
		this.fs.copy("${templatePath}/src/tsconfig.spec.json.vm", "${projectName}/src/tsconfig.spec.json", vars);
		this.fs.copy("${templatePath}/src/typings.d.ts.vm", "${projectName}/src/typings.d.ts.json", vars);
		this.fs.copy("${templatePath}/angular-cli.json.vm", "${projectName}/.angular-cli.json", vars);
		this.fs.copy("${templatePath}/angulardoc.json.vm", "${projectName}/.angulardoc.json", vars);
		this.fs.copy("${templatePath}/editorconfig.vm", "${projectName}/.editorconfig", vars);
		this.fs.copy("${templatePath}/gitignore.vm", "${projectName}/.gitignore", vars);
		this.fs.copy("${templatePath}/gitlab-ci.yml.vm", "${projectName}/.gitlab-ci.yml", vars);
		this.fs.copy("${templatePath}/karma.conf.js.vm", "${projectName}/karma.conf.js", vars);
		this.fs.copy("${templatePath}/package.json.vm", "${projectName}/package.json", vars);
		this.fs.copy("${templatePath}/protractor.conf.js.vm", "${projectName}/protractor.conf.js", vars);
		this.fs.copy("${templatePath}/readme.md.vm", "${projectName}/README.md", vars);

		this.workContext.changeRelativeTo((String) vars.get("projectName"));

		ConsoleLog.print(ConsoleLog.cyan("\t-- npm install --"));

		this.executor.run("npm i && code .");

		// this.pathService.exec("npm i && code .");
	}

}
