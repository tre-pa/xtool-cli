package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgDialogRepresentation;

public class ENgDialogImpl extends ENgClassImpl implements NgDialogRepresentation {

	public ENgDialogImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgDialog [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
