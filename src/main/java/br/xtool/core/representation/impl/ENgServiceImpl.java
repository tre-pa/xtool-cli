package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.ENgService;

public class ENgServiceImpl extends ENgClassImpl implements ENgService {

	public ENgServiceImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgService [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}
}
