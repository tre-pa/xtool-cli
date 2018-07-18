package br.xtool.core.representation.impl;

import java.io.File;

import br.xtool.core.representation.angular.ENgService;

public class ENgServiceImpl extends ENgClassImpl implements ENgService {

	public ENgServiceImpl(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgService [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}
}
