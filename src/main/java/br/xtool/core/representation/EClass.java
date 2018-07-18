package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

public interface EClass extends Comparable<EClass> {

	String getName();

	String getQualifiedName();

	EPackage getPackage();

	boolean hasAnnotation(String name);

	String getPath();

	SortedSet<EField> getFields();

	SortedSet<EAnnotation> getAnnotations();

	SortedSet<EMethod> getMethods();

	Optional<EAnnotation> addAnnotation(String qualifiedName);

	Optional<EField> addField(String qualifiedType, String name);

	void addImport(String importName);

	void save();
}
