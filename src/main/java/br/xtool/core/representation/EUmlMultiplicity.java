package br.xtool.core.representation;

/**
 * Representa a multiplicidade UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlMultiplicity {

	/**
	 * Retorna a multiplicidade mínima.
	 * 
	 * @return
	 */
	String getMin();

	/**
	 * Retorna a multiplicidade máxima.
	 * 
	 * @return
	 */
	String getMax();

	/**
	 * Retorna se a multiplicadade mímina e igual a máxima.
	 * 
	 * @return
	 */
	boolean isMinEqualsMax();
}
