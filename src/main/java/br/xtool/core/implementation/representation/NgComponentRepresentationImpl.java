package br.xtool.core.implementation.representation;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgHtmlTemplateRepresentation;
import br.xtool.core.representation.angular.NgTsClassRepresentation;

public class NgComponentRepresentationImpl extends NgClassRepresentationImpl implements NgComponentRepresentation {

	public NgComponentRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public NgHtmlTemplateRepresentation getNgHtmlTemplate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NgTsClassRepresentation getNgTsClass() {
		throw new UnsupportedOperationException();
	}
}
