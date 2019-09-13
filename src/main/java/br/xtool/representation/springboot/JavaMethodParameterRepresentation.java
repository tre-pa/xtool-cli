package br.xtool.representation.springboot;

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
public interface JavaMethodParameterRepresentation<T extends JavaSource<T>> {

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
	JavaMethodRepresentation<T> getJavaMethod();

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
	SortedSet<JavaAnnotationRepresentation<T>> getAnnotations();

	/**
	 * Retorna o objeto roaster do parametro.
	 * 
	 * @return
	 */
	ParameterSource<T> getRoasterParameter();
}
