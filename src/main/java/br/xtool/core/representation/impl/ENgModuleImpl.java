package br.xtool.core.representation.impl;

import java.io.File;

import br.xtool.core.representation.angular.ENgModule;

/**
 * Classe que representa um módulo Angular
 * 
 * @author jcruz
 *
 */
public class ENgModuleImpl extends ENgClassImpl implements ENgModule {

	public ENgModuleImpl(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgModule [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
