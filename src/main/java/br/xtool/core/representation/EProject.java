package br.xtool.core.representation;

import lombok.Getter;

public class EProject {

	@Getter
	private String path;

	@Getter
	private EDirectory directory;

	public EProject(String path) {
		super();
		this.path = path;
		this.directory = new EDirectory(path);
	}

}
