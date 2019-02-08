package br.xtool.core.representation.springboot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.JavaUnit;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.impl.PomRepresentationImpl;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;

/**
 * Representação de um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public interface SpringBootProjectRepresentation extends ProjectRepresentation {

	/**
	 * Retorna o nome da classe base. O nome da classe base é o nome da classe que
	 * possui a annotation @SpringBootApplication sem o sufixo 'Application'
	 * 
	 * @return Nome da classe base.
	 */
	String getBaseClassName();

	Collection<JavaUnit> getRoasterJavaUnits();

	/**
	 * 
	 * @return
	 */
	JavaClassRepresentation getMainclass();

	/**
	 * Retorna o pacote raiz.
	 * 
	 * @return
	 */
	JavaPackageRepresentation getRootPackage();

	/**
	 * 
	 * @return
	 */
	JavaSourceFolderRepresentation getMainSourceFolder();

	/**
	 * 
	 * @return
	 */
	JavaSourceFolderRepresentation getTestSourceFolder();

	/**
	 * Retorna a representação do arquivo pom.xml
	 * 
	 * @return
	 */
	PomRepresentation getPom();

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	ApplicationPropertiesRepresentation getApplicationProperties();

	/**
	 * 
	 * @return
	 */
	Collection<JavaEnumRepresentation> getEnums();

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	SortedSet<EntityRepresentation> getEntities();

	/**
	 * Retorna a lista de projetions do projeto.
	 * 
	 * @return
	 */
	SortedSet<JpaProjectionRepresentation> getProjections();

	/**
	 * Retorna a lista de specifications do projeto.
	 * 
	 * @return
	 */
	SortedSet<SpecificationRepresentation> getSpecifications();

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	SortedSet<RepositoryRepresentation> getRepositories();

	/**
	 * Restorna a lista de services.
	 * 
	 * @return
	 */
	SortedSet<ServiceClassRepresentation> getServices();

	/**
	 * Retorna a lsta de classes rests.
	 * 
	 * @return
	 */
	SortedSet<RestClassRepresentation> getRests();

	/**
	 * Retorna o projeto Angular associado ao projeto SpringBoot. Por conveção o
	 * projeto angular associado possui o mesmo nome do projeto Spring Boot menos
	 * -service
	 * 
	 * @return
	 */
	Optional<NgProjectRepresentation> getAssociatedAngularProject();

	/**
	 * Retorna o diagrama de classe principal do projeto.
	 * 
	 * @return PlantClassDiagramRepresentation com a representação do diagrama de
	 *         classe.
	 */
	PlantClassDiagramRepresentation getMainDomainClassDiagram();

	/**
	 * Retorna a lista de diagramas de classes localizados em docs/diagrams/class
	 * 
	 * @return Lista de PlantClassDiagramRepresentation
	 */
	List<PlantClassDiagramRepresentation> getClassDiagrams();

	/**
	 * 
	 */
	@Override
	void refresh();

	/**
	 * Verifica se o path possui um projeto spring boot válido.
	 * 
	 * @param path Caminho do projeto
	 * @return
	 */
	static boolean isValid(Path path) {
		Path pomFile = path.resolve("pom.xml");
		if (Files.exists(pomFile)) {
			PomRepresentation ePom = PomRepresentationImpl.of(null, pomFile);
			if (ePom.getParentVersion().isPresent()) {
				return ePom.getParentGroupId().get().equals("org.springframework.boot");
			}
		}
		return false;
	}

}
