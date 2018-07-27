package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.ENgLayout;

public class ENgLayoutImpl extends ENgComponentImpl implements ENgLayout {

	public ENgLayoutImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgLayout [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
