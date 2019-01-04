package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.NgPageRepresentation;

public class ENgPageImpl extends ENgComponentImpl implements NgPageRepresentation {

	public ENgPageImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgPage [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
