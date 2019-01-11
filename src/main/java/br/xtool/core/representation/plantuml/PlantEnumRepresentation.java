package br.xtool.core.representation.plantuml;

import java.util.Collection;

/**
 * Representa um Enum no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface PlantEnumRepresentation {

	/**
	 * Retorna o diagrama de classe.
	 * 
	 * @return
	 */
	PlantClassDiagramRepresentation getClassDiagram();

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
	PlantPackageRepresentation getUmlPackage();

	/**
	 * Retorna a lista de valores do Enum UML.
	 * 
	 * @return
	 */
	Collection<String> getValues();
}
