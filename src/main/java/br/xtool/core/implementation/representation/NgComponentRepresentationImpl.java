package br.xtool.core.implementation.representation;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgTemplateRepresentation;

public class NgComponentRepresentationImpl extends NgClassRepresentationImpl implements NgComponentRepresentation {

	public NgComponentRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public NgTemplateRepresentation getNgTemplate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "ENgComponent [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";

	}
}
