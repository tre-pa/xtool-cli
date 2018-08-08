package br.xtool.core.representation;

import br.xtool.core.representation.impl.EUmlNavigability;

/**
 * Representação de um relacionamento no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlRelationship {

	/**
	 * Retorna a navegabilidade do relacionamento.
	 * 
	 * @return
	 */
	EUmlNavigability getNavigability();

	/**
	 * Retorna se o relacionamento é uma associação.
	 * 
	 * @return
	 */
	boolean isAssociation();

	/**
	 * Retorna se o relacionamento é uma composição.
	 * 
	 * @return
	 */
	boolean isComposition();

	/**
	 * Retorna a classe UML source do relacionamento.
	 * 
	 * @return
	 */
	EUmlClass getSourceClass();

	/**
	 * Retorna a role relativa ao source.
	 * 
	 * @return
	 */
	String getSourceRole();

	/**
	 * Verifica se a classe source é proprietária da relação
	 * 
	 * @return
	 */
	boolean isSourceClassOwner();

	/**
	 * Retorna a classe UML target do relacionamento.
	 * 
	 * @return
	 */
	EUmlClass getTargetClass();

	/**
	 * 
	 * @return
	 */
	String getTargetRole();

	/**
	 * Retorna a multiplicidade do source do relacionamento.
	 * 
	 * @return
	 */
	EUmlMultiplicity getSourceMultiplicity();

	/**
	 * Retorna a multiplicidade target do relacionamento.
	 * 
	 * @return
	 */
	EUmlMultiplicity getTargetMultiplicity();

	boolean isOneToOne();

	boolean isOneToMany();

	boolean isManyToOne();

	boolean isManyToMany();

}
