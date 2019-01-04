package br.xtool.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.representation.NgProjectRepresentation;
import br.xtool.core.representation.ProjectRepresentation;

@Service
public class AngularService {
	
	@Autowired
	private Shell shellService;

	@Autowired
	private Workspace workspace;

	public void newApp(String name) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("projectName", name);
			}
		};
		// @formatter:off
		NgProjectRepresentation project = this.workspace.createProject(
				NgProjectRepresentation.class, 
				ProjectRepresentation.Type.ANGULAR, 
				name, 
				ProjectRepresentation.Version.V7, 
				"dx",
				vars);
		// @formatter:on
		ConsoleLog.print(ConsoleLog.cyan("\t-- npm install --"));
		this.shellService.runCmd(project.getPath(), "npm i && code .", vars);

		this.workspace.setWorkingProject(project);
	}
}
