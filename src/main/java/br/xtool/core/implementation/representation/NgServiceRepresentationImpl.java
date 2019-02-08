package br.xtool.core.implementation.representation;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgServiceRepresentation;

public class NgServiceRepresentationImpl extends NgClassRepresentationImpl implements NgServiceRepresentation {

	public NgServiceRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgService [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}
}
