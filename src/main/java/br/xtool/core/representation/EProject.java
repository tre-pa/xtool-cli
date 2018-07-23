package br.xtool.core.representation;

/**
 * Interface genérica para projetos.
 * 
 * @author jcruz
 *
 */
public interface EProject extends Comparable<EProject> {

	enum Type {
		SPRINGBOOT_PROJECT, ANGULAR_PROJECT, NONE;
	}

	enum Version {
		V1, V2, V3, V4, V5, V6, V7, V8, V9, V10
	}

	EDirectory getDirectory();

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	String getName();

	//	String getMainDir();

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
	public Type getProjectType();

}
