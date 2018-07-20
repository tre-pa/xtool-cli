package br.xtool.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EWorkspace;
import br.xtool.core.representation.impl.EDirectoryImpl;
import br.xtool.core.representation.impl.EWorkspaceImpl;

@Component
public class WorkspaceService {

	@Value("${workspace}")
	private String path;

	public EWorkspace getWorkspace() {
		return new EWorkspaceImpl(EDirectoryImpl.of(this.path));
	}

}
