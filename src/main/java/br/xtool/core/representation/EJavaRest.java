package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

public interface EJavaRest extends EJavaClass {

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
	SortedSet<EJavaMethod> getHttpGETMethods();

	/**
	 * Retorna todos os métodos HTTP Put
	 * 
	 * @return
	 */
	SortedSet<EJavaMethod> getHttpPUTMethods();

	/**
	 * Retorna todos os métodos HTTP Post
	 * 
	 * @return
	 */
	SortedSet<EJavaMethod> getHttpPOSTMethods();

	/**
	 * Retorna todos os métodos HTTP Delete
	 * 
	 * @return
	 */
	public SortedSet<EJavaMethod> getHttpDELETEMethods();

}
