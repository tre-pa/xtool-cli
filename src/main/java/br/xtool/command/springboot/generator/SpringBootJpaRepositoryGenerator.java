package br.xtool.command.springboot.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.provider.EJavaEntityValueProvider;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.impl.EJavaEntityImpl;
import br.xtool.core.service.FileService;
import br.xtool.core.service.ProjectService;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.util.Names;

/**
 * Comando que gera um classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class SpringBootJpaRepositoryGenerator extends SpringBootAware {

	@Autowired
	private FileService fs;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "gen:repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EJavaEntityValueProvider.class) EJavaEntityImpl entity) throws IOException, JDOMException {

		EBootProject bootProject = this.projectService.load(EBootProject.class);

		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("groupId", bootProject.getPom().getGroupId());
		vars.put("repositoryName", Names.asRepositoryClass(entity.getName()));
		vars.put("entity", entity);
		// @formatter:on

		//		this.fs.copy("springboot/1.5.x/repository/repository.java.vm", "src/main/java/${groupId.dir}/repository/${repositoryName}.java", vars);
	}
}
