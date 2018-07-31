package br.xtool.core.representation.impl;

/**
 * Representação da naveganilidade no diagrama de classe.
 * 
 * @author jcruz
 *
 */
public interface EUmlNavigability {

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

	/**
	 * Verifica se a direção da navegabilidade é da classe source para o classe
	 * target.
	 * 
	 * @return
	 */
	boolean isSourceToTargetDirection();

	/**
	 * Verifica se a direção da navegabilidade é da classe target para a classe
	 * source.
	 * 
	 * @return
	 */
	boolean isTargetToSourceDirection();

	/**
	 * Verifica se a navegabilidade é em ambas as direções.
	 * 
	 * @return
	 */
	boolean isBothDirection();

	/**
	 * Verifica se a navegabilidade é em nenhum direção.
	 * 
	 * @return
	 */
	boolean isNoneDirection();
}
