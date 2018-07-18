package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um Repository
 * 
 * @author jcruz
 *
 */
public interface EJavaRepository extends Comparable<EJavaRepository> {

	/**
	 * Nome do Repository
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	EJavaPackage getPackage();

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	Optional<EJavaEntity> getTargetEntity();

}
