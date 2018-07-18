package br.xtool.core.representation;

import java.util.Set;
import java.util.SortedSet;

/**
 * Representação de uma entidade java JPA.
 * 
 * @author jcruz
 *
 */
public interface EJavaEntity extends EJavaClass {

	SortedSet<EJavaAttribute> getAttributes();

	Set<EJavaRelationship> getRelationship();
}
