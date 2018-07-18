package br.xtool.core.representation.impl;

import java.io.File;

import br.xtool.core.representation.ENgLayout;

public class ENgLayoutImpl extends ENgComponentImpl implements ENgLayout {

	public ENgLayoutImpl(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgLayout [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
