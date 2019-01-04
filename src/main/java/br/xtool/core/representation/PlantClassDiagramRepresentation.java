package br.xtool.core.representation;

import java.util.Set;

/**
 * Representa um diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface PlantClassDiagramRepresentation {

	/**
	 * Retorna as classes do diagrama de classe UML.
	 * 
	 * @return
	 */
	Set<PlantClassRepresentation> getClasses();

	/**
	 * Retorna a lista de enums do diagrama de classe UML. Os enums não são
	 * associados diretamente as classes estes serão definidos como atributos de
	 * classe.
	 * 
	 * @return
	 */
	Set<PlantEnumRepresentation> getEnums();

	// /**
	// * Retorna os relacionamento UML.
	// *
	// * @return
	// */
	// @Deprecated
	// Set<EUmlRelationship> getRelationships();
}
