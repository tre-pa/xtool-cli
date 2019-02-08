package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.NoneProjectRepresentation;
import br.xtool.core.representation.ProjectRepresentation;

public class NoneProjectRepresentationImpl extends ProjectRepresentationImpl implements NoneProjectRepresentation {

	public NoneProjectRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String getFrameworkVersion() {
		return "";
	}

	@Override
	public void refresh() {

	}

	@Override
	public Type getProjectType() {
		return ProjectRepresentation.Type.NONE;
	}

	@Override
	public Version getProjectVersion() {
		return Version.NONE;
	}

}
