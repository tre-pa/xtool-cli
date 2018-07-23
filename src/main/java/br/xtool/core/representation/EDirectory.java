package br.xtool.core.representation;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.SortedSet;

import br.xtool.core.representation.EProject.Type;

/**
 * Representação de um diretório do sistema de arquivos.
 * 
 * @author jcruz
 *
 */
public interface EDirectory extends Comparable<EDirectory> {

	/**
	 * Retorna o caminho do diretório.
	 * 
	 * @return
	 */
	Path getPath();

	/**
	 * Retorna o tipo de projeto representado pelo diretório atual.
	 * 
	 * @return
	 */
	Type getProjectType();

	/**
	 * Retorna todos os arquivos recursivamente.
	 * 
	 * @return
	 */
	List<File> getAllFiles();

	/**
	 * Retorna os diretórios.
	 * 
	 * @return
	 */
	SortedSet<EDirectory> getDirectories();

	SortedSet<EDirectory> getAllDirectories();

}
