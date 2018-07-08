package br.xtool.core.representation.angular;

import java.io.File;

public class ENgLayout extends ENgClass {

	public ENgLayout(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgLayout [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
