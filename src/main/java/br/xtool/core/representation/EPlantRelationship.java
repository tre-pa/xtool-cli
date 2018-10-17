package br.xtool.core.representation;

import br.xtool.core.representation.impl.EPlantNavigability;
import net.sourceforge.plantuml.cucadiagram.Link;

/**
 * Representação de um relacionamento no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EPlantRelationship {

	/**
	 * 
	 * @return
	 */
	Link getLink();

	/**
	 * 
	 * @return
	 */
	String getSourceQualifier();

	/**
	 * 
	 * @return
	 */
	String getTargetQualifier();

	/**
	 * Retorna a navegabilidade do relacionamento.
	 * 
	 * @return
	 */
	EPlantNavigability getNavigability();

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
	EPlantClass getSourceClass();

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
	EPlantEntity getTargetClass();

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
	EPlantMultiplicity getSourceMultiplicity();

	/**
	 * Retorna a multiplicidade target do relacionamento.
	 * 
	 * @return
	 */
	EPlantMultiplicity getTargetMultiplicity();

	boolean isOneToOne();

	boolean isOneToMany();

	boolean isManyToOne();

	boolean isManyToMany();

	interface EAssociation extends EPlantRelationship {}

	interface EComposition extends EPlantRelationship {}

}
