package br.xtool.core.representation;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.SortedSet;

import javax.persistence.GenerationType;

import org.hibernate.annotations.LazyCollectionOption;
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

	EJavaClass getJavaClass();

	SortedSet<EJavaAnnotation> getAnnotations();

	EJavaAnnotation addAnnotation(Class<? extends Annotation> type);

	boolean isFinal();

	boolean isPackagePrivate();

	boolean isPublic();

	boolean isPrivate();

	boolean isProtected();

	boolean hasJavaDoc();

	boolean isTransient();

	boolean isVolatile();

	int getLineNumber();

	/**
	 * Retorna o relacionamento JPA.
	 * 
	 * @return
	 */
	Optional<EJavaRelationship> getRelationship();

	FieldSource<JavaClassSource> getRoasterField();

	EJavaAnnotation addSizeAnnotation(Integer min, Integer max);

	EJavaAnnotation addBatchSizeAnnotation(Integer size);

	EJavaAnnotation addLazyCollectionAnnotation(LazyCollectionOption lazyCollectionOption);

	EJavaAnnotation addGeneratedValueAnnotation(GenerationType generationType);

	EJavaAnnotation addSequenceGeneratorAnnotation();
}
