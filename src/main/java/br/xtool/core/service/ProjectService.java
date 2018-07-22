package br.xtool.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.EProject;

@Service
public class ProjectService {

	@Autowired
	private WorkspaceService workspaceService;

	/**
	 * Carrega um projeto do workspace.
	 * 
	 * @param projectClass
	 * @return
	 */
	public <T extends EProject> T load(Class<T> projectClass) {
		return projectClass.cast(this.workspaceService.getWorkingProject());
	}

}
