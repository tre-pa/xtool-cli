package br.xtool.core.representation;

import java.util.SortedSet;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de um campo de classe java.
 * 
 * @author jcruz
 *
 */
public interface EJavaField extends Comparable<EJavaField> {

	String getName();

	Type<JavaClassSource> getType();

	boolean isCollection();

	boolean isStatic();

	SortedSet<EJavaAnnotation> getAnnotations();

	boolean isFinal();

	boolean isPackagePrivate();

	boolean isPublic();

	boolean isPrivate();

	boolean isProtected();

	boolean hasJavaDoc();

	boolean isTransient();

	boolean isVolatile();

	int getLineNumber();

	FieldSource<JavaClassSource> getRoasterField();

}
