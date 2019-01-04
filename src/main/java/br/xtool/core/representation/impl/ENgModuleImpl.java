package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.NgModuleRepresentation;

/**
 * Classe que representa um módulo Angular
 * 
 * @author jcruz
 *
 */
public class ENgModuleImpl extends ENgClassImpl implements NgModuleRepresentation {

	public ENgModuleImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgModule [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
