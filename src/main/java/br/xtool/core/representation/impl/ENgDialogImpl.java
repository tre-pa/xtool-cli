package br.xtool.core.representation.impl;

import java.io.File;

import br.xtool.core.representation.ENgDialog;

public class ENgDialogImpl extends ENgClassImpl implements ENgDialog {

	public ENgDialogImpl(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgDialog [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
