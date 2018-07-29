package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma classe java.
 * 
 * @author jcruz
 *
 */
public interface EJavaClass extends Comparable<EJavaClass> {

	String getName();

	String getQualifiedName();

	EJavaPackage getPackage();

	boolean hasAnnotation(String name);

	Path getPath();

	SortedSet<EJavaField> getFields();

	SortedSet<EJavaAnnotation> getAnnotations();

	SortedSet<EJavaMethod> getMethods();

	JavaClassSource getRoasterJavaClass();

	//	void save();
}
