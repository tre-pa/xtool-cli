package br.xtool.representation.plantuml;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Representação de um atributo de uma classe do diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface PlantClassFieldRepresentation {

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

	/**
	 * Retorna se o atributo é do tipo Long.
	 * 
	 * @return
	 */
	boolean isLong();

	/**
	 * Retorna se o atributo é do tipo Byte.
	 * 
	 * @return
	 */
	boolean isByte();

	/**
	 * Retorna se o atributo é do tipo Boolean.
	 * 
	 * @return
	 */
	boolean isBoolean();

	/**
	 * Retorna se o atributo é do tipo integer.
	 * 
	 * @return
	 */
	boolean isInteger();

	/**
	 * Retorna se o atributo é do tipo LocalDate.
	 * 
	 * @return
	 */
	boolean isLocalDate();

	/**
	 * Retorna se o atributo é do tipo LocalDateTime.
	 * 
	 * @return
	 */
	boolean isLocalDateTime();

	/**
	 * Retorna se o atributo é do tipo BigDecimal.
	 * 
	 * @return
	 */
	boolean isBigDecimal();

	/**
	 * Retorna se o atributo é do tipo BigInteger.
	 * 
	 * @return
	 */
	boolean isBigInteger();

	/**
	 * Retorna se o atributo é do tipo String.
	 * 
	 * @return
	 */
	boolean isString();

	/**
	 * Verifica se o atributo possui multiplicidade.
	 * 
	 * @return
	 */
	boolean hasMultiplicity();

	/**
	 * Retorna se o atributo é um Enum.
	 * 
	 * @return
	 */
	boolean isEnum();

	/**
	 * Retorna a representação do enum.
	 * 
	 * @return
	 */
	Optional<PlantEnumRepresentation> getPlantEnumRepresentation();

	/**
	 * Retorna o limite inferior da multiplicidade do atributo.
	 * 
	 * @return
	 */
	Optional<Integer> getLowerBoundMultiplicity();

	/**
	 * Retorna o limite superior da multiplicidade do atributo.
	 * 
	 * @return
	 */
	Optional<Integer> getUpperBoundMultiplicity();

	/**
	 * 
	 * Verifica se a atributo possui properties.
	 * 
	 * @return
	 */
	boolean hasProperties();

	/**
	 * Verifica se o atributo possui uma propridade.
	 * 
	 * @param propertyName
	 * @return
	 */
	boolean hasProperty(String propertyName);

	/**
	 * Retorna uma property do atributo.
	 * 
	 * @param name
	 * @return
	 */
	Optional<PlantClassFieldPropertyRepresentation> getProperty(PlantClassFieldPropertyRepresentation.FieldPropertyType type);

	/**
	 * 
	 * @param key
	 * @return
	 */
	Optional<String> getTaggedValue(String key);

	/**
	 * Retorna o array de String de uma Tagged Value.
	 * 
	 * @param key
	 * @return
	 */
	Optional<String[]> getTaggedValues(String key);

	@AllArgsConstructor
	@Getter
	enum FieldType {
		// @formatter:off
		LONG("Long", ""), 
		BIGDECIMAL("BigDecimal", "java.math.BigDecimal"),
		BIGINTEGER("BigInteger", "java.math.BigInteger"),
		INTEGER("Integer", ""), 
		STRING("String", ""), 
		BYTE("byte", ""), 
		BOOLEAN("Boolean", ""), 
		LOCALDATE("LocalDate", "java.time.LocalDate"), 
		LOCALDATETIME("LocalDateTime", "java.time.LocalDateTime"), 
		DATE("Date", "java.util.Date"),
		ENUM("", "");
		@Setter
		private String javaName;
		@Setter
		private String className;
	}

}
