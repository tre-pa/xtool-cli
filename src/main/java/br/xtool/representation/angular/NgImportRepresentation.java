package br.xtool.representation.angular;

import java.util.List;

/**
 * Classe que representa um import em um módulo.
 * 
 * @author jcruz
 *
 */
public interface NgImportRepresentation {

	/**
	 * Retorna a lista de itens do import ( valores entre {} )
	 * 
	 * @return
	 */
	List<String> getItems();

	/**
	 * Retorna o nome do caminho do import.
	 * 
	 * @return
	 */
	String getPathName();
}
