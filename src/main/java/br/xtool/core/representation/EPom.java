package br.xtool.core.representation;

import java.util.List;

import org.jdom2.Namespace;

/**
 * Representa o arquivo pom.xml
 * 
 * @author jcruz
 *
 */
public interface EPom {

	public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	/**
	 * Retorna o groupId da aplicação.
	 * 
	 * @return
	 */
	EPackage getGroupId();

	/**
	 * Retorna a versão da aplicação.
	 * 
	 * @return
	 */
	String getVersion();

	/**
	 * Retorna a versão do parent.
	 * 
	 * @return
	 */
	public String getParentVersion();

	/**
	 * Verifica se um atefato existe no pom.xml
	 * 
	 * @param artifactId
	 *            Nome do artefato
	 * @return
	 */
	boolean hasArtifactId(String artifactId);

	/**
	 * Retorna a lista de dependências do projeto.
	 * 
	 * @return
	 */
	List<EDependency> getDependencies();

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	void addDependency(String groupId, String artifactId);

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	void addDependency(String groupId, String artifactId, String version);

	void save();

}
