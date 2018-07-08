package br.xtool.core.representation;

import java.io.File;

public class ENgComponent extends ENgClass {

	public ENgComponent(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgComponent [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";

	}
}
