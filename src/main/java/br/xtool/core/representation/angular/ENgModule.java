package br.xtool.core.representation.angular;

import java.io.File;

/**
 * Classe que representa um módulo Angular
 * 
 * @author jcruz
 *
 */
public class ENgModule extends ENgClass {

	public ENgModule(File file) {
		super(file);
	}

	@Override
	public String toString() {
		return "ENgModule [" + (getName() != null ? "name=" + getName() + ", " : "") + (getFileName() != null ? "fileName=" + getFileName() : "") + "]";
	}

}
