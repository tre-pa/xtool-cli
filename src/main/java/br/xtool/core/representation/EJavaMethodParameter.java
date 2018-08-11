package br.xtool.core.representation;

import java.util.SortedSet;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

/**
 * Representação de um parametro de um método.
 * 
 * @author jcruz
 *
 */
public interface EJavaMethodParameter {

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
	EJavaMethod getJavaMethod();

	/**
	 * 
	 * @return
	 */
	Type<JavaClassSource> getType();

	/**
	 * Retorna as annotation do parametro.
	 * 
	 * @return
	 */
	SortedSet<EJavaAnnotation> getAnnotations();

	/**
	 * Retorna o objeto roaster do parametro.
	 * 
	 * @return
	 */
	ParameterSource<JavaClassSource> getRoasterParameter();
}
