package br.xtool.core.service.impl;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EWorkspace;
import br.xtool.core.representation.impl.ENoneProjectImpl;
import br.xtool.core.representation.impl.EWorkspaceImpl;
import br.xtool.core.service.WorkspaceService;
import lombok.Getter;

@Component
public class WorkspaceServiceImpl implements WorkspaceService {

	@Value("${workspace}")
	private Path home;

	@Getter
	private EProject workingProject;

	@PostConstruct
	private void init() {
		this.workingProject = new ENoneProjectImpl(this.home);
	}

	@Override
	public EWorkspace getWorkspace() {
		return new EWorkspaceImpl(this.home);
	}

	@Override
	public void setWorkingProject(EProject project) {
		this.workingProject = project;
	}

}
