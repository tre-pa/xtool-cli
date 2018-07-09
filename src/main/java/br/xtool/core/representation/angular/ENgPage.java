package br.xtool.core.representation.angular;

import java.io.File;

public class ENgPage extends ENgComponent {

	public ENgPage(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgPage [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
