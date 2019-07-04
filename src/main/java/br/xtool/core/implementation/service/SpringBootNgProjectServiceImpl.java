package br.xtool.core.implementation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.xtool.core.Clog;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.service.SpringBootNgProjectService;

public class SpringBootNgProjectServiceImpl implements SpringBootNgProjectService {

	@Autowired
	private Workspace workspace;

	@Autowired
	private Shell shell;

	@Override
	public SpringBootNgProjectRepresentation newApp(String name, String description, String version) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("projectName", SpringBootNgProjectRepresentation.genProjectName(name));
		vars.put("projectDesc", description);
		vars.put("baseClassName", SpringBootNgProjectRepresentation.genBaseClassName(name));
		vars.put("rootPackage", SpringBootNgProjectRepresentation.genRootPackage(name));
		vars.put("clientSecret", UUID.randomUUID());
		// @formatter:off
		SpringBootNgProjectRepresentation bootProject = this.workspace.createProject(
				ProjectRepresentation.Type.SPRINGBOOTNG, 
				version,
				SpringBootNgProjectRepresentation.genProjectName(name), 
				vars);
		// @formatter:on

		this.workspace.setWorkingProject(bootProject);
		this.shell.runCmd(bootProject.getPath(), "chmod +x scripts/keycloak/register-client.sh");
		this.shell.runCmd(bootProject.getPath(), "git init > /dev/null 2>&1 ");
		this.shell.runCmd(bootProject.getPath(), "git add . > /dev/null 2>&1");
		this.shell.runCmd(bootProject.getPath(), "git commit -m \"Inicial commit\" > /dev/null 2>&1 ");
		Clog.print(Clog.cyan("\t-- Commit inicial realizado no git. --"));

		return bootProject;
	}

}
