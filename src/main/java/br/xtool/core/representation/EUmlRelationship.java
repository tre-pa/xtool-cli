package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um relacionamento no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlRelationship {

	/**
	 * Retorna se o relacionamento é bidirecional
	 * 
	 * @return
	 */
	boolean isBidirectional();

	boolean isUnidirectional();

	EUmlClass getSourceClass();

	EUmlClass getTargetClass();

	Optional<EUmlMultiplicity> getSourceMutiplicity();

	Optional<EUmlMultiplicity> getTargetMultiplicity();

	Optional<String> getSourceRole();

	Optional<String> getTargetRole();

}
