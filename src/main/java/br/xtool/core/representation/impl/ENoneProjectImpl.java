package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.ENoneProject;

public class ENoneProjectImpl extends EProjectImpl implements ENoneProject {

	public ENoneProjectImpl(Path path) {
		super(path);
	}

	@Override
	public String getFrameworkVersion() {
		return "";
	}

	//	@Override
	//	public String getMainDir() {
	//		return "";
	//	}

	@Override
	public void refresh() {

	}

}
