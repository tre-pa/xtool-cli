package br.xtool.generator.core;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.generator.GeneratorCommand;
import strman.Strman;

@ShellGeneratorComponent(templatePath = "generators/springboot/scaffold/1.5.x")
public class NewSpringBootProjectGenerator extends GeneratorCommand {

	@ShellMethod(key = "new-springboot-project", value = "Novo projeto Spring Boot 1.5.x", group = XtoolCliApplication.CORE_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome do projeto") String name,
			@ShellOption(help = "Nome do pacote raiz", defaultValue = "") String packageRoot,
			@ShellOption(help = "Desativa a dependência jpa", value = "--no-jpa", defaultValue = "false", arity = 1) boolean noJpa,
			@ShellOption(help = "Desativa a dependência web", value = "--no-web", defaultValue = "false", arity = 1) boolean noWeb) {

		//// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("projectName", getFinalProjectName(name))
				.build();
		// @formatter:on

		System.out.println(getFinalProjectName(name));
		System.out.println(getFinalPackageRoot(name, packageRoot));
	}

	private String getFinalProjectName(String name) {
		name = name.endsWith("-service") ? name : name.concat("-service");
		return Strman.toKebabCase(name);
	}

	private String getFinalPackageRoot(String name, String packageRoot) {
		name = name.endsWith("-service") ? name.replace("-service", "") : name;
		String _packageRoot = StringUtils.join(StringUtils.split(Strman.toKebabCase(name), "-"), ".");
		return StringUtils.isEmpty(packageRoot) ? "br.jus.tre_pa.".concat(_packageRoot) : packageRoot;
	}
}
