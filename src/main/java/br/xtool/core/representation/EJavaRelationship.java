package br.xtool.core.representation;

import java.util.Optional;

public interface EJavaRelationship {

	/**
	 * Verifica se o relacionamento é bidirecional.
	 * 
	 * @return
	 */
	boolean isBidirectional();

	/**
	 * Verifica se o relacionamento é unidirecional.
	 * 
	 * @return
	 */
	boolean isUnidirectional();

	/**
	 * 
	 * @return
	 */
	EJavaClass getSourceClass();

	/**
	 * 
	 * @return
	 */
	EJavaClass getTargetClass();

	/**
	 * 
	 * @return
	 */
	EJavaField getSourceField();

	/**
	 * 
	 * @return
	 */
	Optional<EJavaField> getTargetField();

}
