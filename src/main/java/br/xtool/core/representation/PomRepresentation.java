package br.xtool.core.representation;

import java.util.List;
import java.util.Optional;

import org.jdom2.Namespace;

import br.xtool.core.representation.impl.EBootPomDependencyImpl.ScopeType;

/**
 * Representação do arquivo pom.xml do projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface PomRepresentation {

	public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	SpringBootProjectRepresentation getProject();

	/**
	 * Retorna o groupId da aplicação.
	 * 
	 * @return
	 */
	JavaPackageRepresentation getGroupId();

	/**
	 * Retorna a versão da aplicação.
	 * 
	 * @return
	 */
	String getVersion();

	/**
	 * 
	 * @return
	 */
	Optional<String> getParentGroupId();

	/**
	 * Retorna a versão do parent.
	 * 
	 * @return
	 */
	Optional<String> getParentVersion();

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
	List<PomDependencyRepresentation> getDependencies();

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	PomRepresentation addDependency(String groupId, String artifactId);

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	PomRepresentation addDependency(String groupId, String artifactId, String version);

	/**
	 * 
	 * @param groupId
	 * @param artifactId
	 * @param scopeType
	 * @return
	 */
	PomRepresentation addDependency(String groupId, String artifactId, ScopeType scopeType);

	void save();

}
