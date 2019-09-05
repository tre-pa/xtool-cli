package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Interface genérica para projetos.
 * 
 * @author jcruz
 *
 */
public interface ProjectRepresentation extends Comparable<ProjectRepresentation> {

	/**
	 * Retorna o path do projeto.
	 * 
	 * @return
	 */
	Path getPath();

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna a versão do projeto.
	 * 
	 * @return
	 */
	String getVersion();

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	String getType();

	/**
	 * Lista todos os arquivo do projeto recursivamente.
	 * 
	 * @return
	 */
	Collection<Path> listAllFiles();

	/**
	 * Lista todos os diretórios recursivamente.
	 * 
	 * @return
	 */
	Collection<Path> listAllDirectories();

	/**
	 * Efetua uma limpeza no cache do projeto.
	 */
	void refresh();
}
