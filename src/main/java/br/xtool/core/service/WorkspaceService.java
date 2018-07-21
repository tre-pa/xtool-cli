package br.xtool.core.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.xtool.core.event.ChangeWorkingProjectEvent;
import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EWorkspace;
import br.xtool.core.representation.impl.EDirectoryImpl;
import br.xtool.core.representation.impl.ENoneProjectImpl;
import br.xtool.core.representation.impl.EWorkspaceImpl;
import lombok.Getter;

@Component
public class WorkspaceService {

	@Value("${workspace}")
	private String path;

	@Getter
	private EProject workingProject;

	@PostConstruct
	private void init() {
		this.workingProject = new ENoneProjectImpl(EDirectoryImpl.of(this.path));
	}

	public EWorkspace getWorkspace() {
		return new EWorkspaceImpl(EDirectoryImpl.of(this.path));
	}

	@EventListener
	private void onChangeWorkingProject(ChangeWorkingProjectEvent evt) {
		this.workingProject = evt.getProject();
	}

}
