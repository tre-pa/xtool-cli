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
	 * Verifica se o atributo é um Boolean.
	 * 
	 * @return
	 */
	boolean isBooleanField();

	/**
	 * Verifica se o atributo é um Long
	 * 
	 * @return
	 */
	boolean isLongField();

	/**
	 * Verifica se o atributo é um Integer.
	 * 
	 * @return
	 */
	boolean isIntegerField();

	/**
	 * Verifica se o atributo é um Byte.
	 * 
	 * @return
	 */
	boolean isByteField();

	/**
	 * Verifica se o atributo é um BigDecimal.
	 * 
	 * @return
	 */
	boolean isBigDecimalField();

	/**
	 * Verifica se o atributo é um número (Long, Integer, Short, Byte, BigiDecimal)
	 * 
	 * @return
	 */
	boolean isNumberField();

	/**
	 * Verifica se o atributo é um LocalDate
	 * 
	 * @return
	 */
	boolean isLocalDate();

	/**
	 * Verifica se o atributo é um LocalDateTime
	 * 
	 * @return
	 */
	boolean isLocalDateTime();

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
	 * Verifica se o atributo é NotNull
	 * 
	 * @return
	 */
	boolean isNotNullField();

	/**
	 * Verifica se o atributo é Unique.
	 * 
	 * @return
	 */
	boolean isUniqueField();

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

	@Deprecated
	interface JavaFieldStringType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldBooleanType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldLongType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldIntegerType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldByteType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldBigDecimalType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldLocalDateType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldLocalDateTimeType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldEnumType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldNotNullType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldTransientType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldUniqueType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldOneToOneType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldOneToManyType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldManyToOneType extends JavaFieldRepresentation {}

	@Deprecated
	interface JavaFieldManyToManyType extends JavaFieldRepresentation {}

}
