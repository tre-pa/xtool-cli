package br.xtool.generator.core;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.FS;
import br.xtool.core.Log;
import br.xtool.core.NamePattern;
import br.xtool.core.command.RegularCommand;
import strman.Strman;

/**
 * Shell Commando responsável por criar uma projeto Spring Boot 1.5.x
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class NewSpringBootProjectGenerator extends RegularCommand {

	@Autowired
	private FS fs;

	@ShellMethod(key = "new-springboot-project", value = "Novo projeto Spring Boot 1.5.x", group = XtoolCliApplication.PROJECT_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Nome do projeto") String name, 
			@ShellOption(help = "Versão do projeto", defaultValue = "0.0.1") String version,
			@ShellOption(help = "Nome do pacote raiz", defaultValue = "") String packageRoot,
			@ShellOption(help = "Desativa a dependência jpa", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desativa a dependência web", defaultValue = "false", arity = 0) boolean noWeb) throws IOException {
	// @formatter:on

		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/springboot/scaffold/1.5.x")
				.put("projectName", NamePattern.asSpringBootProject(name))
				.put("projectVersion", version)
				.put("packageRoot", getFinalPackageRoot(name, packageRoot))
				.put("baseClassName", NamePattern.asSpringBootBaseClass(name))
				.put("noJpa", noJpa)
				.put("noWeb", noWeb)
				.build();
		// @formatter:on

		Log.print("");

		fs.copy("${templatePath}/src/main/java/config/gitkeep", "${projectName}/src/main/java/${packageRoot.dir}/config/.gitkeep", vars);
		fs.copy("${templatePath}/src/main/java/domain/gitkeep", "${projectName}/src/main/java/${packageRoot.dir}/domain/.gitkeep", vars);
		fs.copy("${templatePath}/src/main/java/exception/gitkeep", "${projectName}/src/main/java/${packageRoot.dir}/exception/.gitkeep", vars);
		fs.copy("${templatePath}/src/main/java/report/gitkeep", "${projectName}/src/main/java/${packageRoot.dir}/report/.gitkeep", vars);
		fs.copy("${templatePath}/src/main/java/repository/gitkeep", "${projectName}/src/main/java/${packageRoot.dir}/repository/.gitkeep", vars);
		fs.copy("${templatePath}/src/main/java/rest/gitkeep", "${projectName}/src/main/java/${packageRoot.dir}/rest/.gitkeep", vars);
		fs.copy("${templatePath}/src/main/java/service/gitkeep", "${projectName}/src/main/java/${packageRoot.dir}/service/.gitkeep", vars);
		fs.copy("${templatePath}/src/main/java/SpringBootApplication.java.vm", "${projectName}/src/main/java/${packageRoot.dir}/${baseClassName}Application.java", vars);
		fs.copy("${templatePath}/src/main/resources/application.properties.vm", "${projectName}/src/main/resources/application.properties", vars);
		fs.copy("${templatePath}/src/main/resources/ehcache.xml.vm", "${projectName}/src/main/resources/ehcache.xml", vars);
		fs.copy("${templatePath}/gitignore", "${projectName}/.gitignore", vars);
		fs.copy("${templatePath}/pom.xml.vm", "${projectName}/pom.xml", vars);

		Log.print("");

	}

	/*
	 * Retorna o nome final do pacote raiz do projeto.
	 */
	private Map<String, String> getFinalPackageRoot(String name, String packageRoot) {
		name = name.endsWith("-service") ? name.replace("-service", "") : name;
		String _packageRoot = StringUtils.join(StringUtils.split(Strman.toKebabCase(name), "-"), ".");
		String packageName = StringUtils.isEmpty(packageRoot) ? "br.jus.tre_pa.".concat(_packageRoot) : packageRoot;
		String packageDir = packageName.replaceAll("\\.", "/");
		// @formatter:off
		return ImmutableMap.<String, String>builder()
				.put("name", packageName)
				.put("dir", packageDir)
				.build();
		// @formatter:on
	}

}
