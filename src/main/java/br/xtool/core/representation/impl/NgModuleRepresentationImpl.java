package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.angular.NgModuleRepresentation;

/**
 * Classe que representa um módulo Angular
 * 
 * @author jcruz
 *
 */
public class NgModuleRepresentationImpl extends NgClassRepresentationImpl implements NgModuleRepresentation {

	public NgModuleRepresentationImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgModule [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
