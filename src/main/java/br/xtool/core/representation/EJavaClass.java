package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

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

	String getPath();

	SortedSet<EJavaField> getFields();

	SortedSet<EJavaAnnotation> getAnnotations();

	SortedSet<EJavaMethod> getMethods();

	Optional<EJavaAnnotation> addAnnotation(String qualifiedName);

	Optional<EJavaField> addField(String qualifiedType, String name);

	void addImport(String importName);

	void save();
}
