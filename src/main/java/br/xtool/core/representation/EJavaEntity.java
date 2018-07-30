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

	/**
	 * Retorna os atributos JPA da entidade.
	 * 
	 * @return
	 */
	SortedSet<EJavaAttribute> getAttributes();

	/**
	 * Retorna os relacionamentos da entidade.
	 * 
	 * @return
	 */
	Set<EJavaRelationship> getRelationships();
}
