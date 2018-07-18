package br.xtool.core.representation.impl;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.EProject;
import lombok.Getter;

public abstract class EProjectImpl implements EProject {

	@Getter
	private String path;

	@Getter
	private EDirectory directory;

	public EProjectImpl(String path) {
		super();
		this.path = path;
		this.directory = EDirectoryImpl.of(path);
	}

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.getDirectory().getBaseName();
	}

	@Override
	public abstract String getMainDir();

	@Override
	public abstract void refresh();

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	@Override
	public ProjectType getProjectType() {
		return this.getDirectory().getProjectType();
	}

}
