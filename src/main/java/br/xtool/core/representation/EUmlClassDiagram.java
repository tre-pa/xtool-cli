package br.xtool.core.representation;

import java.util.Set;

/**
 * Representa um diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlClassDiagram {

	/**
	 * Retorna as classes do diagrama de classe UML.
	 * 
	 * @return
	 */
	Set<EUmlClass> getClasses();

	/**
	 * Retorna a lista de enums do diagrama de classe UML.
	 * 
	 * @return
	 */
	Set<EUmlEnum> getEnums();

	/**
	 * 
	 */
	Set<EUmlRelationship> getRelationships();
}
