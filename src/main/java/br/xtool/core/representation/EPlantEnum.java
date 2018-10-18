package br.xtool.core.representation;

import java.util.Collection;

/**
 * Representa um Enum no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EPlantEnum {

	/**
	 * Retorna o diagrama de classe.
	 * 
	 * @return
	 */
	EPlantClassDiagram getClassDiagram();

	/**
	 * Retorna o nome da classe UML.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 
	 * @return
	 */
	String getInstanceName();

	/**
	 * Retorna o nome qualificado. Pacote + Nome.
	 * 
	 * @return
	 */
	String getQualifiedName();

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	EPlantPackage getUmlPackage();

	/**
	 * Retorna a lista de valores do Enum UML.
	 * 
	 * @return
	 */
	Collection<String> getValues();
}
