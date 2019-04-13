package br.xtool.core.representation.angular;

import java.nio.file.Path;

/**
 * 
 * @author jcruz
 *
 */
public interface NgTypeRepresentation {

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
