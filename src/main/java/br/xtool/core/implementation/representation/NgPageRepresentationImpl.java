package br.xtool.core.implementation.representation;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgPageRepresentation;

public class NgPageRepresentationImpl extends NgComponentRepresentationImpl implements NgPageRepresentation {

	public NgPageRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgPage [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
