package br.xtool.core.implementation;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.xtool.core.FS;
import br.xtool.core.Workspace;
import br.xtool.core.implementation.representation.NoneProjectRepresentationImpl;
import br.xtool.core.implementation.representation.WorkspaceRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.WorkspaceRepresentation;
import lombok.Getter;

@Service
public class WorkspaceImpl implements Workspace {

	@Value("${workspace}")
	private Path home;

	@Getter
	private ProjectRepresentation workingProject;

	@Autowired
	private FS fs;

	@PostConstruct
	private void init() {
		workingProject = new NoneProjectRepresentationImpl(home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#getWorkspace()
	 */
	@Override
	public WorkspaceRepresentation getWorkspace() {
		return new WorkspaceRepresentationImpl(home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#setWorkingProject(br.xtool.core. representation.EProject)
	 */
	@Override
	public void setWorkingProject(ProjectRepresentation project) {
		workingProject = project;
	}


}
