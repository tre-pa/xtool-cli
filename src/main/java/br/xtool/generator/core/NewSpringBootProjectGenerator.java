package br.xtool.generator.core;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Log;
import br.xtool.core.PathService;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.command.XCommand;
import strman.Strman;

/**
 * Shell Commando responsável por criar uma projeto Spring Boot 1.5.x
 * 
 * @author jcruz
 *
 */
@ShellGeneratorComponent(templatePath = "generators/springboot/scaffold/1.5.x")
public class NewSpringBootProjectGenerator extends XCommand {

	@Autowired
	private PathService pathService;

	@Autowired
	private Log log;

	@ShellMethod(key = "new-springboot-project", value = "Novo projeto Spring Boot 1.5.x", group = XtoolCliApplication.CORE_COMMAND_GROUP)
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
				.put("projectName", getFinalProjectName(name))
				.put("projectVersion", version)
				.put("packageRoot", getFinalPackageRoot(name, packageRoot))
				.put("mainClassName", getMainClassName(getFinalProjectName(name)))
				.put("noJpa", noJpa)
				.put("noWeb", noWeb)
				.build();
		// @formatter:on

		this.setDestinationRoot(getFinalProjectName(name));
		log.print("");
		this.copyTpl("src/main/java/config/gitkeep", "src/main/java/${packageRoot.dir}/config/.gitkeep", vars);
		this.copyTpl("src/main/java/domain/gitkeep", "src/main/java/${packageRoot.dir}/domain/.gitkeep", vars, () -> !noJpa);
		this.copyTpl("src/main/java/exception/gitkeep", "src/main/java/${packageRoot.dir}/exception/.gitkeep", vars);
		this.copyTpl("src/main/java/report/gitkeep", "src/main/java/${packageRoot.dir}/report/.gitkeep", vars);
		this.copyTpl("src/main/java/repository/gitkeep", "src/main/java/${packageRoot.dir}/repository/.gitkeep", vars, () -> !noJpa);
		this.copyTpl("src/main/java/rest/gitkeep", "src/main/java/${packageRoot.dir}/rest/.gitkeep", vars, () -> !noWeb);
		this.copyTpl("src/main/java/service/gitkeep", "src/main/java/${packageRoot.dir}/service/.gitkeep", vars);
		this.copyTpl("src/main/java/SpringBootApplication.java.vm", "src/main/java/${packageRoot.dir}/${mainClassName}Application.java", vars);
		this.copyTpl("src/main/resources/application.properties.vm", "src/main/resources/application.properties", vars);
		this.copy("src/main/resources/ehcache.xml.vm", "src/main/resources/ehcache.xml", () -> !noJpa);
		this.copy("gitignore", ".gitignore");
		this.copyTpl("pom.xml.vm", "pom.xml", vars);

		this.changeWorkingDirectoryToDestinationRoot();
	}

	/*
	 * Retorna o nome final do projeto com o sufixo -service caso necessário.
	 */
	private String getFinalProjectName(String name) {
		name = name.endsWith("-service") ? name : name.concat("-service");
		return Strman.toKebabCase(name);
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

	private String getMainClassName(String finalProjectName) {
		return Strman.toStudlyCase(finalProjectName);
	}

}
