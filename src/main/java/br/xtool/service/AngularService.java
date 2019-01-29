package br.xtool.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;

@Service
@Lazy
public class AngularService {
	
	@Autowired
	private Shell shellService;

	@Autowired
	private Workspace workspace;

	public void newApp(String name, String qualifier) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("projectName", name);
			}
		};
		// @formatter:off
		NgProjectRepresentation project = this.workspace.createProject(
				ProjectRepresentation.Type.ANGULAR, 
				name, 
				qualifier,
				vars);
		// @formatter:on
		ConsoleLog.print(ConsoleLog.cyan("\t-- npm install --"));
		this.shellService.runCmd(project.getPath(), "npm i && code .", vars);
		this.shellService.runCmd(project.getPath(), "git init");
		this.shellService.runCmd(project.getPath(), "git add .");
		this.shellService.runCmd(project.getPath(), "git commit -m \"Inicial commit\" ");

		this.workspace.setWorkingProject(project);
	}
}
