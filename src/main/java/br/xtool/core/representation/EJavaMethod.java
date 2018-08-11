package br.xtool.core.representation;

import java.util.Collection;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * Representação de um método java.
 * 
 * @author jcruz
 *
 */
public interface EJavaMethod extends Comparable<EJavaMethod> {

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

	Type<JavaClassSource> getReturnType();

	Collection<EJavaMethodParameter> getParameters();

	SortedSet<EJavaAnnotation> getAnnotations();

	MethodSource<JavaClassSource> getRoasterMethod();
}
