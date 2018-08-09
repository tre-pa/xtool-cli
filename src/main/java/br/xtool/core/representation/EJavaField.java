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

	EBootProject getProject();

	String getName();

	Type<JavaClassSource> getType();

	boolean isCollection();

	boolean isStatic();

	EJavaClass getJavaClass();

	SortedSet<EJavaAnnotation> getAnnotations();

	EJavaAnnotation addAnnotation(Class<? extends Annotation> type);

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

	interface EStringField extends EJavaField {}

	interface EBooleanField extends EJavaField {}

	interface ELongField extends EJavaField {}

	interface EIntegerField extends EJavaField {}

	interface EByteField extends EJavaField {}

	interface EBigDecimalField extends EJavaField {}

	interface ELocalDateField extends EJavaField {}

	interface ELocalDateTimeField extends EJavaField {}

	interface ENotNullField extends EJavaField {}

	interface ETransientField extends EJavaField {}

	interface EUniqueField extends EJavaField {}

	interface EOneToOneField extends EJavaField {}

	interface EOneToManyField extends EJavaField {}

	interface EManyToOneField extends EJavaField {}

	interface EManyToManyField extends EJavaField {}

}
