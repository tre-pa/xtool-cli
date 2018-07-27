package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.ENoneProject;
import br.xtool.core.representation.EProject;

public class ENoneProjectImpl extends EProjectImpl implements ENoneProject {

	public ENoneProjectImpl(Path path) {
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
		return EProject.Type.NONE;
	}

	@Override
	public Version getProjectVersion() {
		return Version.NONE;
	}

}
