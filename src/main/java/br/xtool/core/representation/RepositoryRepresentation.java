package br.xtool.core.representation;

/**
 * Representação de um Repository
 * 
 * @author jcruz
 *
 */
public interface RepositoryRepresentation extends JavaInterfaceRepresentation {

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	@Override
	JavaPackageRepresentation getJavaPackage();

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	EntityRepresentation getTargetEntity();

	/**
	 * Retorna a projeção alvo do repostório.
	 * 
	 * @return
	 */
	JpaProjectionRepresentation getTargetProjection();

	/**
	 * Retorna a specification alvo do repositório.
	 * 
	 * @return
	 */
	SpecificationRepresentation getTargetSpecification();

}
