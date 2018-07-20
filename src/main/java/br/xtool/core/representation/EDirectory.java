package br.xtool.core.representation;

import java.io.File;
import java.util.List;

import br.xtool.core.representation.EProject.ProjectType;

/**
 * Representação de um diretório do sistema de arquivos.
 * 
 * @author jcruz
 *
 */
public interface EDirectory {

	/**
	 * Retorna o caminho do diretório.
	 * 
	 * @return
	 */
	String getPath();

	/**
	 * Retorna o tipo de projeto representado pelo diretório atual.
	 * 
	 * @return
	 */
	ProjectType getProjectType();

	/**
	 * Retorna todos os arquivos recursivamente.
	 * 
	 * @return
	 */
	List<File> getAllFiles();

	/**
	 * Retorna todos os diretórios.
	 * 
	 * @return
	 */
	List<EDirectory> getChildrenDirectories();

}
