package br.xtool.core.representation;

import java.util.List;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

public interface EMethod extends Comparable<EMethod> {

	String getName();

	boolean isStatic();

	boolean isConstructor();

	boolean hasAnnotation(String type);

	Type<JavaClassSource> getReturnType();

	int getLineNumber();

	boolean isReturnTypeVoid();

	List<ParameterSource<JavaClassSource>> getParameters();

	SortedSet<EAnnotation> getAnnotations();
}
