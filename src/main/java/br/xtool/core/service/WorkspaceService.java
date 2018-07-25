package br.xtool.core.service;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EWorkspace;
import br.xtool.core.representation.impl.ENoneProjectImpl;
import br.xtool.core.representation.impl.EWorkspaceImpl;
import lombok.Getter;

@Component
public class WorkspaceService {

	@Value("${workspace}")
	@Getter
	private Path home;

	@Getter
	private EProject workingProject;

	@PostConstruct
	private void init() {
		this.workingProject = new ENoneProjectImpl(this.home);
	}

	public EWorkspace getWorkspace() {
		return new EWorkspaceImpl(this.home);
	}

	public void use(EProject project) {
		this.workingProject = project;
	}

}
