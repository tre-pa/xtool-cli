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

	@AllArgsConstructor
	@Getter
	enum FieldType {
		// @formatter:off
		BIGDECIMAL("BigDecimal", "java.math.BigDecimal"),
		LONG("Long", ""), 
		INTEGER("Integer", ""), 
		STRING("String", ""), 
		BYTE("byte", ""), 
		BOOLEAN("Boolean", ""), 
		LOCALDATE("LocalDate", "java.time.LocalDate"), 
		LOCALDATETIME("LocalDateTime", "java.time.LocalDateTime"), 
		DATE("Date", "java.util.Date");
		private String javaName;
		private String importName;
		// @formatter:on
	}

	/**
	 * Retorna o nome do atributo.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna se o atributo é o Id da classe.
	 * 
	 * @return
	 */
	boolean isId();

	/**
	 * Retorna se o atributo é um array.
	 * 
	 * @return
	 */
	boolean isArray();

	Optional<Integer> getMinArrayLength();

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

}
