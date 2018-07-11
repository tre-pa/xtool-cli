package br.xtool.command.springboot.archetype;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.google.common.collect.ImmutableMap;

import br.xtool.XtoolCliApplication;
import br.xtool.command.springboot.support.core.SupportManager;
import br.xtool.command.springboot.support.core.SpringBootSupport.SupportType;
import br.xtool.core.ConsoleLog;
import br.xtool.core.FS;
import br.xtool.core.Names;
import br.xtool.core.WorkContext;
import br.xtool.core.command.RegularCommand;
import br.xtool.core.representation.EPackage;
import br.xtool.core.representation.ESpringBootProject;

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

	@Autowired
	private WorkContext workContext;

	@Autowired
	private SupportManager supportManager;

	@ShellMethod(key = "new:springboot", value = "Novo projeto Spring Boot 1.5.x", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Nome do projeto") String name, 
			@ShellOption(help = "Versão do projeto", defaultValue = "0.0.1") String version,
			@ShellOption(help = "Nome do pacote raiz", defaultValue = "") String rootPackage,
			@ShellOption(help = "Desativa a dependência jpa", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desativa a dependência web", defaultValue = "false", arity = 0) boolean noWeb) throws IOException {
	// @formatter:on

		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/springboot/archetype/1.5.x")
				.put("projectName", Names.asSpringBootProject(name))
				.put("projectVersion", version)
				.put("rootPackage", getFinalRootPackage(name, rootPackage))
				.put("baseClassName", Names.asSpringBootBaseClass(name))
				.put("noJpa", noJpa)
				.put("noWeb", noWeb)
				.build();
		// @formatter:on

		ConsoleLog.print(ConsoleLog.cyan("\t-- Projeto Base --"));
		this.fs.createEmptyPath("${projectName}/src/main/java/${rootPackage.dir}/config", vars);
		this.fs.createEmptyPath("${projectName}/src/main/java/${rootPackage.dir}/exception", vars);
		this.fs.createEmptyPath("${projectName}/src/main/java/${rootPackage.dir}/service", vars);
		this.fs.createEmptyPath("${projectName}/src/main/java/${rootPackage.dir}/report", vars);
		this.fs.copy("${templatePath}/src/main/java/SpringBootApplication.java.vm", "${projectName}/src/main/java/${rootPackage.dir}/${baseClassName}Application.java", vars);
		this.fs.copy("${templatePath}/src/main/resources/application.properties.vm", "${projectName}/src/main/resources/application.properties", vars);
		this.fs.copy("${templatePath}/gitignore", "${projectName}/.gitignore", vars);
		this.fs.copy("${templatePath}/pom.xml.vm", "${projectName}/pom.xml", vars);

		this.workContext.changeRelativeTo((String) vars.get("projectName"));

		if (this.workContext.getSpringBootProject().isPresent()) {
			ESpringBootProject project = this.workContext.getSpringBootProject().get();
			if (!noJpa) this.supportManager.addSupport(project, SupportType.JPA);
			if (!noWeb) this.supportManager.addSupport(project, SupportType.WEB);
		}

	}

	/*
	 * Retorna o nome final do pacote raiz do projeto.
	 */
	private EPackage getFinalRootPackage(String name, String rootPackage) {
		String packageName = StringUtils.isEmpty(rootPackage) ? "br.jus.tre_pa.".concat(Names.asDotCase(name)) : rootPackage;
		return EPackage.of(packageName);
	}

}
