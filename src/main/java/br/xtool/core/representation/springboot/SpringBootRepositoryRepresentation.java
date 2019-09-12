package br.xtool.core.representation.springboot;

/**
 * Representação de um Repository
 * 
 * @author jcruz
 *
 */
public interface SpringBootRepositoryRepresentation extends JavaInterfaceRepresentation {

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
	JpaEntityRepresentation getTargetEntity();

	/**
	 * Retorna a projeção alvo do repostório.
	 * 
	 * @return
	 */
	SpringBootProjectionRepresentation getTargetProjection();

	/**
	 * Retorna a specification alvo do repositório.
	 * 
	 * @return
	 */
	SpringBooSpecificationRepresentation getTargetSpecification();

}
