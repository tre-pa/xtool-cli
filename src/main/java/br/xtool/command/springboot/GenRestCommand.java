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
import br.xtool.core.util.Names;
import br.xtool.service.FileService;
import br.xtool.service.WorkspaceService;

@ShellComponent
public class GenRestCommand extends SpringBootAware {

	@Autowired
	private FileService fs;

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "gen:rest", value = "Gera uma classe Rest em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Nome da classe Rest") String name) throws JDOMException, IOException {

		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);

		/*
		 * Cria o mapa com as variáveis do gerador.
		 */
		//// @formatter:off
		Map<String, Object> vars = new HashMap<>();
		vars.put("groupId", bootProject.getPom().getGroupId());
		vars.put("restName", Names.asRestClass(name));
		vars.put("restPath", Names.asRestPath(name));
		// @formatter:on

		//		this.fs.copy("springboot/1.5.x/rest/rest.java.vm", "src/main/java/${groupId.dir}/rest/${restName}.java", vars);
	}
}
