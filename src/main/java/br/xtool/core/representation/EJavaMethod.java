package br.xtool.core.representation;

import java.util.List;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

/**
 * Representação de um método java.
 * 
 * @author jcruz
 *
 */
public interface EJavaMethod extends Comparable<EJavaMethod> {

	EBootProject getProject();

	EJavaClass getJavaClass();

	String getName();

	Type<JavaClassSource> getReturnType();

	List<ParameterSource<JavaClassSource>> getParameters();

	SortedSet<EJavaAnnotation> getAnnotations();

	MethodSource<JavaClassSource> getRoasterMethod();
}
