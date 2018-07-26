package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.ENgModule;

/**
 * Classe que representa um módulo Angular
 * 
 * @author jcruz
 *
 */
public class ENgModuleImpl extends ENgClassImpl implements ENgModule {

	public ENgModuleImpl(Path path) {
		super(path);
	}

	@Override
	public String toString() {
		return "ENgModule [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
