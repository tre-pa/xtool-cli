package br.xtool.core.representation;

import java.util.Optional;
import java.util.Set;

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
	 * Retorna de o atributo é unico.
	 * 
	 * @return
	 */
	boolean isUnique();

	/**
	 * Retorna se o atributo é não nulo.
	 * 
	 * @return
	 */
	boolean isNotNull();

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
	Set<String> getProperties();

	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean hasProperty(String name);

}
