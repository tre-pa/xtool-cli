package br.xtool.representation.springboot;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.SortedSet;

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
	 * Retorna o label do projeto.
	 * 
	 * @return
	 */
	String getLabel();

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	Type<JavaClassSource> getType();

	/**
	 * Verifica se o atribito é simples (Number, String, Boolean, LocalDate,
	 * LocalDateTime)
	 * 
	 * @return
	 */
	boolean isSimpleField();

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

	/**
	 * Adiciona uma nova annotation ao atributo. Caso já exista retorna a
	 * referencia.
	 * 
	 * @param type
	 * @return
	 */
	JavaAnnotationRepresentation<JavaClassSource> addAnnotation(Class<? extends Annotation> type);

	/**
	 * Retorna a objeto Roaster do atributo.
	 * 
	 * @return
	 */
	FieldSource<JavaClassSource> getRoasterField();

}
