package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;

/**
 * Representação de um campo de classe java.
 * 
 * @author jcruz
 *
 */
public interface EJavaField extends Comparable<EJavaField> {

	String getName();

	FieldSource<JavaClassSource> setName(String name);

	Type<JavaClassSource> getType();

	boolean isCollection();

	boolean isStatic();

	boolean hasAnnotation(String name);

	void setStringInitialize(String value);

	void setLiteralInitialize(String value);

	SortedSet<EJavaAnnotation> getAnnotations();

	Optional<EJavaAnnotation> addAnnotation(String qualifiedName);

	boolean isFinal();

	boolean isPackagePrivate();

	boolean isPublic();

	boolean isPrivate();

	FieldSource<JavaClassSource> setFinal(boolean finl);

	FieldSource<JavaClassSource> setStatic(boolean value);

	boolean isProtected();

	boolean hasJavaDoc();

	Visibility getVisibility();

	JavaDocSource<FieldSource<JavaClassSource>> getJavaDoc();

	FieldSource<JavaClassSource> setPackagePrivate();

	FieldSource<JavaClassSource> setPublic();

	boolean isTransient();

	FieldSource<JavaClassSource> setPrivate();

	FieldSource<JavaClassSource> setType(Class<?> clazz);

	FieldSource<JavaClassSource> setProtected();

	FieldSource<JavaClassSource> setVisibility(Visibility scope);

	boolean isVolatile();

	FieldSource<JavaClassSource> setType(String type);

	int getLineNumber();

	FieldSource<JavaClassSource> setType(JavaType<?> entity);

	FieldSource<JavaClassSource> setLiteralInitializer(String value);

	FieldSource<JavaClassSource> setStringInitializer(String value);

	FieldSource<JavaClassSource> setTransient(boolean value);

	FieldSource<JavaClassSource> setVolatile(boolean value);

}
