package br.xtool.core.representation;

import java.util.Optional;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public interface ERepository extends Comparable<ERepository> {

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
	EPackage getPackage();

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	Optional<EEntity> getTargetEntity();

}
