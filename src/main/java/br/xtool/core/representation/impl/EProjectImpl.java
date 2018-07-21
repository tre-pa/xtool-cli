package br.xtool.core.representation.impl;

import org.apache.commons.io.FilenameUtils;

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
		return FilenameUtils.getBaseName(this.getDirectory().getPath());
	}

	//	@Override
	//	public abstract String getMainDir();

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

	@Override
	public int compareTo(EProject o) {
		return this.getName().compareTo(o.getName());
	}

}
