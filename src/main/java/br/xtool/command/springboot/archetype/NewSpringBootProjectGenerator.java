package br.xtool.command.springboot.archetype;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.command.springboot.support.core.SupportManager;
import br.xtool.core.aware.RegularAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EProject;
import br.xtool.core.service.FileService;
import br.xtool.core.service.ProjectService;
import br.xtool.core.service.WorkspaceService;

/**
 * Shell Commando responsável por criar uma projeto Spring Boot 1.5.x
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class NewSpringBootProjectGenerator extends RegularAware {

	@Autowired
	private FileService fs;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SupportManager supportManager;

	@ShellMethod(key = "new:springboot", value = "Novo projeto Spring Boot 1.5.x", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Nome do projeto") String name, 
			@ShellOption(help = "Versão do projeto", defaultValue = "0.0.1") String version,
			@ShellOption(help = "Desativa a dependência jpa", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desativa a dependência web", defaultValue = "false", arity = 0) boolean noWeb) throws IOException {
	// @formatter:on

		EBootProject bootProject = this.projectService.create(EBootProject.class, EProject.Version.V1, name);

		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("templatePath", "springboot/1.5.x/archetype");
		vars.put("projectName", bootProject.getName());
		vars.put("projectVersion", version);
		vars.put("rootPackage", bootProject.getRootPackage());
//		vars.put("baseClassName", Names.asSpringBootBaseClass(name));
		vars.put("noJpa", noJpa);
		vars.put("noWeb", noWeb);
		// @formatter:on

		//		this.fs.createEmptyPath("${projectName}/src/main/java/${rootPackage.dir}/config", vars);
		//		this.fs.createEmptyPath(bootProject.getMainSourceFolder(), "config");
		//		this.fs.createEmptyPath(bootProject.getMainSourceFolder(), "exception");
		//		this.fs.createEmptyPath(bootProject.getMainSourceFolder(), "service");
		//		this.fs.createEmptyPath("${projectName}/src/main/java/${rootPackage.dir}/report", vars);
		//		this.fs.copy("${templatePath}/src/main/java/SpringBootApplication.java.vm", "${projectName}/src/main/java/${rootPackage.dir}/${baseClassName}Application.java", vars);
		//		this.fs.copy("${templatePath}/src/main/resources/application.properties.vm", "${projectName}/src/main/resources/application.properties", vars);
		//		this.fs.copy("${templatePath}/gitignore", "${projectName}/.gitignore", vars);
		//		this.fs.copy("${templatePath}/pom.xml.vm", "${projectName}/pom.xml", vars);

		//		ESBootProject bootProject = ESBootProjectImpl.load(directory)
		//		this.workContext.changeRelativeTo((String) vars.get("projectName"));

		//		ESBootProject project = this.workContext.getSpringBootProject();
		//		if (!noJpa) this.supportManager.addSupport(project, SupportType.JPA);
		//		if (!noWeb) this.supportManager.addSupport(project, SupportType.WEB);

	}

	//	/*
	//	 * Retorna o nome final do pacote raiz do projeto.
	//	 */
	//	private EJavaPackage getFinalRootPackage(String name, String rootPackage) {
	//		String packageName = StringUtils.isEmpty(rootPackage) ? EJavaPackage.getDefaultPrefix().concat(Names.asDotCase(name)) : rootPackage;
	//		return EJavaPackageImpl.of(packageName);
	//	}

}
