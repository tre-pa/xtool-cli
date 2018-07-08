package br.xtool.core.representation.angular;

import java.io.File;

public class ENgPage extends ENgClass {

	public ENgPage(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgPage [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
