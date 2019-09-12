package br.xtool.core.representation.springboot;

import java.util.Optional;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma classe Rest.
 * 
 * @author jcruz
 *
 */
public interface SpringBootRestClassRepresentation extends JavaClassRepresentation {

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
	SortedSet<JavaMethodRepresentation<JavaClassSource>> getHttpGETMethods();

	/**
	 * Retorna todos os métodos HTTP Put
	 * 
	 * @return
	 */
	SortedSet<JavaMethodRepresentation<JavaClassSource>> getHttpPUTMethods();

	/**
	 * Retorna todos os métodos HTTP Post
	 * 
	 * @return
	 */
	SortedSet<JavaMethodRepresentation<JavaClassSource>> getHttpPOSTMethods();

	/**
	 * Retorna todos os métodos HTTP Delete
	 * 
	 * @return
	 */
	public SortedSet<JavaMethodRepresentation<JavaClassSource>> getHttpDELETEMethods();

}
