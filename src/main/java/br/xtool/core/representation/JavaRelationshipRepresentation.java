package br.xtool.core.representation;

import java.util.Optional;

public interface JavaRelationshipRepresentation {

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
	JavaClassRepresentation getSourceClass();

	/**
	 * 
	 * @return
	 */
	JavaClassRepresentation getTargetClass();

	/**
	 * 
	 * @return
	 */
	JavaFieldRepresentation getSourceField();

	/**
	 * 
	 * @return
	 */
	Optional<JavaFieldRepresentation> getTargetField();

}
