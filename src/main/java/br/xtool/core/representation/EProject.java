package br.xtool.core.representation;

/**
 * Interface genérica para projetos.
 * 
 * @author jcruz
 *
 */
public interface EProject extends Comparable<EProject> {

	public enum ProjectType {
		SPRINGBOOT_PROJECT, ANGULAR_PROJECT, NONE;
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
