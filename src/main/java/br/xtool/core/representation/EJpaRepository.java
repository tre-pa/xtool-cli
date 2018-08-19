package br.xtool.core.representation;

/**
 * Representação de um Repository
 * 
 * @author jcruz
 *
 */
public interface EJpaRepository extends EJavaInterface {

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	@Override
	EJavaPackage getJavaPackage();

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	EJpaEntity getTargetEntity();

	/**
	 * Retorna a projeção alvo do repostório.
	 * 
	 * @return
	 */
	EJpaProjection getTargetProjection();

}
