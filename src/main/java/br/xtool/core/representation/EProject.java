package br.xtool.core.representation;

import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

public abstract class EProject {

	@Getter
	private String path;

	@Getter
	private EDirectory directory;

	public EProject(String path) {
		super();
		this.path = path;
		this.directory = EDirectory.of(path);
	}

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	public String getName() {
		return this.getDirectory().getBaseName();
	}

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	public ProjectType getProjectType() {
		return this.getDirectory().getProjectType();
	}
}
