package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.ENgDialog;

public class ENgDialogImpl extends ENgClassImpl implements ENgDialog {

	public ENgDialogImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgDialog [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
