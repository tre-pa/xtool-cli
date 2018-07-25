package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.Collection;

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

	Path getPath();

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
	Type getProjectType();

	/**
	 * Lista todos os arquivo do projeto recursivamente.
	 * 
	 * @return
	 */
	Collection<Path> listAllFiles();

	Collection<Path> listDirectories();

	Collection<Path> listAllDirectories();

}
