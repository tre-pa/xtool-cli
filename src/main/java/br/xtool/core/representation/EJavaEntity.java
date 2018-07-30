package br.xtool.core.representation;

import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.lang3.StringUtils;

import strman.Strman;

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

	/**
	 * Retorna um nome válido de uma tabela do banco de dados com no máximo 30
	 * carateres (Limite do oracle).
	 * 
	 * @param name
	 * @return
	 */
	static String genDBTableName(String name) {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
						Strman.toSnakeCase(name)), "", 30);
		// @formatter:on
	}

	/**
	 * Retorna um nome válido de sequence do banco de dados.
	 * 
	 * @param name
	 * @return
	 */
	public static String genDBSequenceName(String name) {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
				"SEQ_" + Strman.toSnakeCase(name)), "", 30);
		// @formatter:on
	}
}
