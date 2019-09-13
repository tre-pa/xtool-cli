package br.xtool.representation.plantuml;

/**
 * Representação da navegabilidade no diagrama de classe.
 * 
 * @author jcruz
 *
 */
public interface PlantNavigabilityRepresentation {

	/**
	 * Verifica se a navegabilidade é bidirecional.
	 * 
	 * @return
	 */
	boolean isBidirectional();

	/**
	 * Verifica se a navegabilidade é unidirecional.
	 * 
	 * @return
	 */
	boolean isUnidirectional();

}
