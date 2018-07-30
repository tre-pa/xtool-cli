package br.xtool.command.springboot;

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
import br.xtool.core.representation.EBootProject;
import br.xtool.core.service.FileService;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.util.Names;

@ShellComponent
public class GenServiceCommand extends SpringBootAware {

	@Autowired
	private FileService fs;

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "gen:service", value = "Gera uma classe Service em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe Service") String name) throws JDOMException, IOException {

		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);

		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("groupId", bootProject.getPom().getGroupId());
		vars.put("serviceName", Names.asServiceClass((name)));
		// @formatter:on

		//		this.fs.copy("springboot/1.5.x/service/service.java.vm", "src/main/java/${groupId.dir}/service/${serviceName}.java", vars);
	}

}
