package br.xtool.core.representation.angular;

import java.io.File;

public class ENgDialog extends ENgClass {

	public ENgDialog(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgDialog [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
