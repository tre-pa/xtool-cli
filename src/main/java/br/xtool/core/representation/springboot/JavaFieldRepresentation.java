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
 * Representação de um atributo de uma classe java.
 * 
 * @author jcruz
 *
 */
public interface JavaFieldRepresentation extends Comparable<JavaFieldRepresentation> {

	/**
	 * Retorna o nome do atributo.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	Type<JavaClassSource> getType();

	/**
	 * Verifica se o atributo é uma coleção (Tipo List ou Set)
	 * 
	 * @return
	 */
	boolean isCollectionField();

	/**
	 * Verifica se o atributo é uma String.
	 * 
	 * @return
	 */
	boolean isStringField();

	/**
	 * Verifica se o atributo é um número.
	 * 
	 * @return
	 */
	boolean isNumberField();

	/**
	 * Verifica se o atributo é uma data
	 * 
	 * @return
	 */
	boolean isTemporalField();

	/**
	 * Verifica se o atributo é estático.
	 * 
	 * @return
	 */
	boolean isStaticField();

	/**
	 * Verifica se atributo é do tipo Enum.
	 * 
	 * @return
	 */
	boolean isEnumField();

	/**
	 * Verifica se o atributo é um relacionamento.
	 * 
	 * @return
	 */
	boolean isRelationshipField();

	/**
	 * Retorna a representação do Enum do atributo.
	 * 
	 * @return
	 */
	Optional<JavaEnumRepresentation> getEnum();

	/**
	 * Retorna o relacionamento JPA.
	 * 
	 * @return
	 */
	Optional<JavaRelationshipRepresentation> getRelationship();

	/**
	 * Retorna a classe do atributo.
	 * 
	 * @return
	 */
	JavaClassRepresentation getJavaClass();

	/**
	 * Retorna as annotation associadas ao atributo.
	 * 
	 * @return
	 */
	SortedSet<JavaAnnotationRepresentation<JavaClassSource>> getAnnotations();

	FieldSource<JavaClassSource> getRoasterField();

	@Deprecated
	JavaAnnotationRepresentation<JavaClassSource> addAnnotation(Class<? extends Annotation> type);

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

	interface JavaFieldStringType extends JavaFieldRepresentation {}

	interface JavaFieldBooleanType extends JavaFieldRepresentation {}

	interface JavaFieldLongType extends JavaFieldRepresentation {}

	interface JavaFieldIntegerType extends JavaFieldRepresentation {}

	interface JavaFieldByteType extends JavaFieldRepresentation {}

	interface JavaFieldBigDecimalType extends JavaFieldRepresentation {}

	interface JavaFieldLocalDateType extends JavaFieldRepresentation {}

	interface JavaFieldLocalDateTimeType extends JavaFieldRepresentation {}

	interface JavaFieldEnumType extends JavaFieldRepresentation {}

	interface JavaFieldNotNullType extends JavaFieldRepresentation {}

	interface JavaFieldTransientType extends JavaFieldRepresentation {}

	interface JavaFieldUniqueType extends JavaFieldRepresentation {}

	interface JavaFieldOneToOneType extends JavaFieldRepresentation {}

	interface JavaFieldOneToManyType extends JavaFieldRepresentation {}

	interface JavaFieldManyToOneType extends JavaFieldRepresentation {}

	interface JavaFieldManyToManyType extends JavaFieldRepresentation {}

}
