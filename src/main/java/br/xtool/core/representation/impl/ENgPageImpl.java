package br.xtool.core.representation.impl;

import java.io.File;

import br.xtool.core.representation.angular.ENgPage;

public class ENgPageImpl extends ENgComponentImpl implements ENgPage {

	public ENgPageImpl(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgPage [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
