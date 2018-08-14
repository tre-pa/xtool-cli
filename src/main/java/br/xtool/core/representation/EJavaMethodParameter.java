package br.xtool.core.representation;

import java.util.SortedSet;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

/**
 * Representação de um parametro de um método.
 * 
 * @author jcruz
 *
 */
public interface EJavaMethodParameter<T extends JavaSource<T>> {

	/**
	 * Retorna o nome do parametro.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o JavaMethod.
	 * 
	 * @return
	 */
	EJavaMethod<T> getJavaMethod();

	/**
	 * 
	 * @return
	 */
	Type<T> getType();

	/**
	 * Retorna as annotation do parametro.
	 * 
	 * @return
	 */
	SortedSet<EJavaAnnotation<T>> getAnnotations();

	/**
	 * Retorna o objeto roaster do parametro.
	 * 
	 * @return
	 */
	ParameterSource<T> getRoasterParameter();
}
