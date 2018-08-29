package br.xtool.core.representation;

/**
 * Representação de um entidade do relacionamento, podendo representar tanto o
 * source quanto o target da relação.
 * 
 * @author jcruz
 *
 */
public interface EUmlRelationshipEntity {

	/**
	 * Retorna a multiplicidade de entidade no relacionamento.
	 * 
	 * @return
	 */
	EUmlMultiplicity getMultiplicity();

	/**
	 * Retorna a role da entidade no relacionamento.
	 * 
	 * @return
	 */
	String getRole();

	/**
	 * Verifica se o relacionamento é opcional.
	 * 
	 * @return
	 */
	boolean isOptional();

}
