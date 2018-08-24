package br.xtool.core.representation;

/**
 * Representação genérica de uma classe, enum ou interface do diagrama de
 * classe.
 * 
 * @author jcruz
 *
 */
public interface EUmlEntity {

	/**
	 * Retorna o nome da classe UML.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 
	 * @return
	 */
	String getInstanceName();

	/**
	 * Retorna o nome qualificado.
	 * 
	 * @return
	 */
	String getQualifiedName();

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	EUmlPackage getUmlPackage();
}
