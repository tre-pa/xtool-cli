package br.xtool.core.representation;

import java.util.Collection;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * Representação de um método java.
 * 
 * @author jcruz
 *
 */
public interface EJavaMethod<T extends JavaSource<T>> extends Comparable<EJavaMethod<T>> {

	/**
	 * Retorna o objeto JavaClass do método
	 * 
	 * @return
	 */
	EJavaClass getJavaClass();

	/**
	 * Retorna o nome do método.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o tipo de retorno do método.
	 * 
	 * @return
	 */
	Type<T> getReturnType();

	/**
	 * Retorna os parametros do método.
	 * 
	 * @return
	 */
	Collection<EJavaMethodParameter<T>> getParameters();

	/**
	 * Retorna as annotations do método.
	 * 
	 * @return
	 */
	SortedSet<EJavaAnnotation<T>> getAnnotations();

	/**
	 * Retorna o objeto MethodSource.
	 * 
	 * @return
	 */
	MethodSource<T> getRoasterMethod();
}
