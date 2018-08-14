package br.xtool.core.representation;

/**
 * Representação de um Repository
 * 
 * @author jcruz
 *
 */
public interface EBootRepository extends Comparable<EBootRepository>, EJavaInterface {

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	EJavaPackage getJavaPackage();

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	EJpaEntity getTargetEntity();

}
