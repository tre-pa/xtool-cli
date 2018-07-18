package br.xtool.core.representation;

/**
 * Representação de um relacionamento no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlRelationship {

	boolean isBidirectional();

	boolean isUnidirectional();

}
