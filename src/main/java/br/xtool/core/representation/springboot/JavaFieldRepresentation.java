package br.xtool.core.representation.springboot;

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
public interface JavaFieldRepresentation extends Comparable<JavaFieldRepresentation> {

	String getName();

	Type<JavaClassSource> getType();

	boolean isCollection();

	boolean isStatic();

	Optional<JavaEnumRepresentation> getEnum();

	/**
	 * 
	 * @return
	 */
	boolean isEnumField();

	JavaClassRepresentation getJavaClass();

	SortedSet<JavaAnnotationRepresentation<JavaClassSource>> getAnnotations();

	JavaAnnotationRepresentation<JavaClassSource> addAnnotation(Class<? extends Annotation> type);

	/**
	 * Retorna o relacionamento JPA.
	 * 
	 * @return
	 */
	Optional<JavaRelationshipRepresentation> getRelationship();

	boolean isRelationshipField();

	FieldSource<JavaClassSource> getRoasterField();

	JavaAnnotationRepresentation<JavaClassSource> addSizeAnnotation(Integer min, Integer max);

	JavaAnnotationRepresentation<JavaClassSource> addBatchSizeAnnotation(Integer size);

	JavaAnnotationRepresentation<JavaClassSource> addLazyCollectionAnnotation(LazyCollectionOption lazyCollectionOption);

	JavaAnnotationRepresentation<JavaClassSource> addGeneratedValueAnnotation(GenerationType generationType);

	JavaAnnotationRepresentation<JavaClassSource> addSequenceGeneratorAnnotation();

	interface EStringField extends JavaFieldRepresentation {
	}

	interface EBooleanField extends JavaFieldRepresentation {
	}

	interface ELongField extends JavaFieldRepresentation {
	}

	interface EIntegerField extends JavaFieldRepresentation {
	}

	interface EByteField extends JavaFieldRepresentation {
	}

	interface EBigDecimalField extends JavaFieldRepresentation {
	}

	interface ELocalDateField extends JavaFieldRepresentation {
	}

	interface ELocalDateTimeField extends JavaFieldRepresentation {
	}

	interface EEnumField extends JavaFieldRepresentation {
	}

	interface ENotNullField extends JavaFieldRepresentation {
	}

	interface ETransientField extends JavaFieldRepresentation {
	}

	interface EUniqueField extends JavaFieldRepresentation {
	}

	interface EOneToOneField extends JavaFieldRepresentation {
	}

	interface EOneToManyField extends JavaFieldRepresentation {
	}

	interface EManyToOneField extends JavaFieldRepresentation {
	}

	interface EManyToManyField extends JavaFieldRepresentation {
	}

}
