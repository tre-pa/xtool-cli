package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

public interface ERest extends EClass {

	/**
	 * Retorna o contexto raiz da api rest.
	 * 
	 * @return Contexto raiz
	 */
	Optional<String> getRootPath();

	/**
	 * Retorna todos os métodos HTTP Get
	 * 
	 * @return
	 */
	SortedSet<EMethod> getHttpGETMethods();

	/**
	 * Retorna todos os métodos HTTP Put
	 * 
	 * @return
	 */
	SortedSet<EMethod> getHttpPUTMethods();

	/**
	 * Retorna todos os métodos HTTP Post
	 * 
	 * @return
	 */
	SortedSet<EMethod> getHttpPOSTMethods();

	/**
	 * Retorna todos os métodos HTTP Delete
	 * 
	 * @return
	 */
	public SortedSet<EMethod> getHttpDELETEMethods();

}
