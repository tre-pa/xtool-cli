package br.xtool.core.service;

import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EDirectory;
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
	private EDirectory home;

	@Getter
	private EProject workingProject;

	@PostConstruct
	private void init() {
		this.workingProject = new ENoneProjectImpl(EDirectoryImpl.of(Paths.get(this.path)));
		this.home = EDirectoryImpl.of(Paths.get(this.path));
	}

	public EWorkspace getWorkspace() {
		return new EWorkspaceImpl(EDirectoryImpl.of(Paths.get(this.path)));
	}

	public void use(EProject project) {
		this.workingProject = project;
	}

}
