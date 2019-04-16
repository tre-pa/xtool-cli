package br.xtool.core.implementation.representation;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgTsClassRepresentation;

public class NgTsClassRepresentationImpl extends NgClassRepresentationImpl implements NgTsClassRepresentation {

	public NgTsClassRepresentationImpl(Path path) {
		super(path);
	}

}
