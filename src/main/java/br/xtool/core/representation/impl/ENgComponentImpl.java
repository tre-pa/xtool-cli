package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.ENgComponent;

public class ENgComponentImpl extends ENgClassImpl implements ENgComponent {

	public ENgComponentImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgComponent [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";

	}
}
