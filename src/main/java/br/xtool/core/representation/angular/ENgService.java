package br.xtool.core.representation.angular;

import java.io.File;

public class ENgService extends ENgClass {

	public ENgService(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgService [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}
}
