package br.xtool.core.representation;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import br.xtool.core.representation.EPlantFieldProperty.FieldPropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Representação de um campo de uma classe do diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EPlantField {

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
	 * Retorna se o atributo é do tipo array de bytes.
	 * 
	 * @return
	 */
	@Deprecated
	boolean isByteArray();

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
	 * Retorna se o atributo é do tipo String.
	 * 
	 * @return
	 */
	boolean isString();

	/**
	 * Retorna se o atributo é um array.
	 * 
	 * @return
	 */
	boolean isArray();
	
	boolean isEnum();

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
	Set<EPlantFieldProperty> getProperties();

	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean hasProperty(FieldPropertyType propertyType);

	Map<String, String> getTaggedValues();

	/**
	 * 
	 * @param key
	 * @return
	 */
	Optional<String> getTaggedValue(String key);

	Optional<String[]> getTaggedValues(String key);

	//	@Deprecated
	//	EJavaField convertToJavaField(EJavaClass javaClass);

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
		DATE("Date", "java.util.Date"),
		ENUM("", "");
		private String javaName;
		private String className;
		// @formatter:on
		public void setJavaName(String javaName) {
			this.javaName = javaName;
		}
		public void setClassName(String className) {
			this.className = className;
		}
	}

}