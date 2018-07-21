package br.xtool.core.representation.impl;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENoneProject;

public class ENoneProjectImpl extends EProjectImpl implements ENoneProject {

	public ENoneProjectImpl(EDirectory directory) {
		super(directory);
	}

	@Override
	public String getFrameworkVersion() {
		return "";
	}

	@Override
	public String getMainDir() {
		return "";
	}

	@Override
	public void refresh() {

	}

}
