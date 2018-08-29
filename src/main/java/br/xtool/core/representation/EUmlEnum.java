package br.xtool.core.representation;

import java.util.Collection;

/**
 * Representa um Enum no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlEnum extends EUmlEntity {

	/**
	 * Retorna o diagrama de classe.
	 * 
	 * @return
	 */
	EUmlClassDiagram getClassDiagram();

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	@Override
	EUmlPackage getUmlPackage();

	/**
	 * Retorna a lista de valores do Enum UML.
	 * 
	 * @return
	 */
	Collection<String> getValues();
}
