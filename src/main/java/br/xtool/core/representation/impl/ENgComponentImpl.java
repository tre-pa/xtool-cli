package br.xtool.core.representation.impl;

import java.io.File;

import br.xtool.core.representation.angular.ENgComponent;

public class ENgComponentImpl extends ENgClassImpl implements ENgComponent {

	public ENgComponentImpl(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgComponent [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";

	}
}
