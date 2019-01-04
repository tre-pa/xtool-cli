package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.NgComponentRepresentation;
import br.xtool.core.representation.NgTemplateRepresentation;

public class ENgComponentImpl extends ENgClassImpl implements NgComponentRepresentation {

	public ENgComponentImpl(Path path) {
		super(path);
	}

	@Override
	public NgTemplateRepresentation getNgTemplate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "ENgComponent [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";

	}
}
