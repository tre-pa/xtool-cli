package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.NgLayoutRepresentation;

public class ENgLayoutImpl extends ENgComponentImpl implements NgLayoutRepresentation {

	public ENgLayoutImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgLayout [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
