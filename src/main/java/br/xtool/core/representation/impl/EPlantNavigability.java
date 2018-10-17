package br.xtool.core.representation.impl;

/**
 * Representação da naveganilidade no diagrama de classe.
 * 
 * @author jcruz
 *
 */
public interface EPlantNavigability {

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
