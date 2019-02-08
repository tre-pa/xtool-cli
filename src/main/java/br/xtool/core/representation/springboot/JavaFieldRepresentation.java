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

	@Deprecated
	JavaAnnotationRepresentation<JavaClassSource> addSizeAnnotation(Integer min, Integer max);

	@Deprecated
	JavaAnnotationRepresentation<JavaClassSource> addBatchSizeAnnotation(Integer size);

	@Deprecated
	JavaAnnotationRepresentation<JavaClassSource> addLazyCollectionAnnotation(LazyCollectionOption lazyCollectionOption);

	@Deprecated
	JavaAnnotationRepresentation<JavaClassSource> addGeneratedValueAnnotation(GenerationType generationType);

	@Deprecated
	JavaAnnotationRepresentation<JavaClassSource> addSequenceGeneratorAnnotation();

	@Deprecated
	interface EStringField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EBooleanField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface ELongField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EIntegerField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EByteField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EBigDecimalField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface ELocalDateField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface ELocalDateTimeField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EEnumField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface ENotNullField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface ETransientField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EUniqueField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EOneToOneField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EOneToManyField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EManyToOneField extends JavaFieldRepresentation {
	}

	@Deprecated
	interface EManyToManyField extends JavaFieldRepresentation {
	}

}
