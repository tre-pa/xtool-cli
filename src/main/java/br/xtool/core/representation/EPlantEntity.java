package br.xtool.core.representation;

/**
 * Representação genérica de uma classe, enum ou interface do diagrama de
 * classe.
 * 
 * @author jcruz
 *
 */
public interface EPlantEntity {

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
	 * Retorna o nome qualificado. Pacote + Nome.
	 * 
	 * @return
	 */
	String getQualifiedName();

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	EPlantPackage getUmlPackage();
}
