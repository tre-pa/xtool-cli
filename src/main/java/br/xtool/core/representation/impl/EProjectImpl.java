package br.xtool.core.representation.impl;

import java.nio.file.Path;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.EProject;
import lombok.Getter;

public abstract class EProjectImpl implements EProject {

	@Getter
	private EDirectory directory;

	public EProjectImpl(Path path) {
		super();
		this.directory = EDirectoryImpl.of(path);
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
	public int compareTo(EProject o) {
		return this.getName().compareTo(o.getName());
	}

}
