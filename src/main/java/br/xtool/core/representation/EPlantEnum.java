package br.xtool.core.representation;

import java.util.Collection;

/**
 * Representa um Enum no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EPlantEnum extends EPlantEntity {

	/**
	 * Retorna o diagrama de classe.
	 * 
	 * @return
	 */
	EPlantClassDiagram getClassDiagram();

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	@Override
	EPlantPackage getUmlPackage();

	/**
	 * Retorna a lista de valores do Enum UML.
	 * 
	 * @return
	 */
	Collection<String> getValues();
}
