package br.xtool.core.representation;

import java.util.Set;

/**
 * Representa um diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EPlantClassDiagram {

	/**
	 * Retorna as classes do diagrama de classe UML.
	 * 
	 * @return
	 */
	Set<EPlantClass> getClasses();

	/**
	 * Retorna a lista de enums do diagrama de classe UML. Os enums não são
	 * associados diretamente as classes estes serão definidos como atributos de
	 * classe.
	 * 
	 * @return
	 */
	Set<EPlantEnum> getEnums();

	// /**
	// * Retorna os relacionamento UML.
	// *
	// * @return
	// */
	// @Deprecated
	// Set<EUmlRelationship> getRelationships();
}
