package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um Repository
 * 
 * @author jcruz
 *
 */
public interface EBootRepository extends Comparable<EBootRepository> {

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
	Optional<EJpaEntity> getTargetEntity();

}
