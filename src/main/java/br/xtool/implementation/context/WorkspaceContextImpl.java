package br.xtool.implementation.context;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import br.xtool.implementation.representation.NoneProjectRepresentationImpl;
import br.xtool.implementation.representation.WorkspaceRepresentationImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.xtool.core.WorkspaceContext;
import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.WorkspaceRepresentation;
import lombok.Getter;

@Service
public class WorkspaceContextImpl implements WorkspaceContext {

	@Value("${workspace}")
	private Path home;

	@Getter
	private ProjectRepresentation workingProject;

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
