package br.xtool.core.representation;

import java.util.Set;

/**
 * Representa um Enum no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlEnum {

	String getName();

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	EUmlPackage getPackage();

	/**
	 * Retorna a lista de valores do Enum UML.
	 * 
	 * @return
	 */
	Set<String> getValues();
}
