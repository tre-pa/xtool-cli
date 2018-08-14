package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma classe Rest.
 * 
 * @author jcruz
 *
 */
public interface EBootRest extends EJavaClass {

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
	SortedSet<EJavaMethod<JavaClassSource>> getHttpGETMethods();

	/**
	 * Retorna todos os métodos HTTP Put
	 * 
	 * @return
	 */
	SortedSet<EJavaMethod<JavaClassSource>> getHttpPUTMethods();

	/**
	 * Retorna todos os métodos HTTP Post
	 * 
	 * @return
	 */
	SortedSet<EJavaMethod<JavaClassSource>> getHttpPOSTMethods();

	/**
	 * Retorna todos os métodos HTTP Delete
	 * 
	 * @return
	 */
	public SortedSet<EJavaMethod<JavaClassSource>> getHttpDELETEMethods();

}
