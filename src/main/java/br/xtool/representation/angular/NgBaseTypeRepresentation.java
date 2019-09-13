package br.xtool.representation.angular;

import java.nio.file.Path;

/**
 * 
 * @author jcruz
 *
 */
public interface NgBaseTypeRepresentation {

	/**
	 * Retorna o caminho
	 * 
	 * @return
	 */
	Path getPath();

	/**
	 * Retorna o nome do arquivo typescript.
	 * 
	 * @return
	 */
	String getTsFileName();
	
	/**
	 * Retorna o conteúdo do arquivo typescript.
	 * 
	 * @return
	 */
	String getTsFileContent();
	
}
