package br.xtool.core.representation;

import java.util.Optional;
import java.util.Set;

import br.xtool.core.representation.EUmlFieldProperty.FieldPropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representação de um campo de uma classe do diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlField {

	/**
	 * Retorna o nome do atributo.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o tipo do atributo
	 * 
	 * @return
	 */
	FieldType getType();

	/**
	 * Retorna se o atributo é o Id da classe.
	 * 
	 * @return
	 */
	boolean isId();

	boolean isLong();

	boolean isByteArray();

	boolean isBoolean();

	boolean isInteger();

	boolean isLocalDate();

	boolean isLocalDateTime();

	boolean isBigDecimal();

	boolean isString();

	/**
	 * Retorna se o atributo é um array.
	 * 
	 * @return
	 */
	boolean isArray();

	/**
	 * Retorna o valor mínimo do attributo array.
	 * 
	 * @return
	 */
	Optional<Integer> getMinArrayLength();

	/**
	 * Retorna o valor máximo do atributo array.
	 * 
	 * @return
	 */
	Optional<Integer> getMaxArrayLength();

	/**
	 * 
	 * @return
	 */
	boolean hasProperties();

	/**
	 * 
	 * @return
	 */
	Set<EUmlFieldProperty> getProperties();

	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean hasProperty(FieldPropertyType propertyType);

	@AllArgsConstructor
	@Getter
	enum FieldType {
		// @formatter:off
		LONG("Long", ""), 
		BIGDECIMAL("BigDecimal", "java.math.BigDecimal"),
		INTEGER("Integer", ""), 
		STRING("String", ""), 
		BYTE("byte", ""), 
		BOOLEAN("Boolean", ""), 
		LOCALDATE("LocalDate", "java.time.LocalDate"), 
		LOCALDATETIME("LocalDateTime", "java.time.LocalDateTime"), 
		DATE("Date", "java.util.Date");
		private String javaName;
		private String className;
		// @formatter:on
	}

}
