package br.xtool.core.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Interface genérica para projetos.
 * 
 * @author jcruz
 *
 */
public interface EProject {

	@AllArgsConstructor
	public enum ProjectType {
		// @formatter:off
		SPRINGBOOT1_PROJECT("Projeto Spring Boot v1.5.x"),
		SPRINGBOOT2_PROJECT("Projeto Spring Boot v2.x.x"),
		ANGULAR5_PROJECT("Projeto Angular v5"), 
		ANGULAR6_PROJECT("Projeto Angular v6"),
		NONE("");
		// @formatter:on
		@Getter
		private String detail;

	}

	EDirectory getDirectory();

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	String getName();

	String getMainDir();

	/**
	 * 
	 * @return
	 */
	String getFrameworkVersion();

	void refresh();

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	public ProjectType getProjectType();

}
