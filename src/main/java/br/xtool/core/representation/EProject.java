package br.xtool.core.representation;

import br.xtool.core.representation.enums.ProjectType;
import br.xtool.core.representation.impl.EDirectoryImpl;
import lombok.Getter;

public abstract class EProject {

	@Getter
	private String path;

	@Getter
	private EDirectory directory;

	public EProject(String path) {
		super();
		this.path = path;
		this.directory = EDirectoryImpl.of(path);
	}

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	public String getName() {
		return this.getDirectory().getBaseName();
	}

	public abstract String getMainDir();

	public abstract void refresh();

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	public ProjectType getProjectType() {
		return this.getDirectory().getProjectType();
	}

}
