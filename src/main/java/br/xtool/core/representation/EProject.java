package br.xtool.core.representation;

import br.xtool.core.representation.enums.ProjectType;

public interface EProject {

	String getPath();

	EDirectory getDirectory();

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	String getName();

	String getMainDir();

	void refresh();

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	public ProjectType getProjectType();

}
