package br.xtool.core.visitor;

/**
 * 
 * @author jcruz
 *
 */
public interface Visitable {

	/**
	 * 
	 * @param visitor
	 */
	void accept(Visitor visitor);
}
