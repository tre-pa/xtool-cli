package br.xtool.core.representation.impl;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.EProject;
import lombok.Getter;

public abstract class EProjectImpl implements EProject {

	@Getter
	private EDirectory directory;

	public EProjectImpl(EDirectory directory) {
		super();
		this.directory = directory;
	}

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.getDirectory().getPath().getFileName().toString();
	}

	//	@Override
	//	public abstract String getMainDir();

	@Override
	public abstract void refresh();

	/**
	 * 
	 */
	@Override
	public Type getProjectType() {
		return this.getDirectory().getProjectType();
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(EProject o) {
		return this.getName().compareTo(o.getName());
	}

}
