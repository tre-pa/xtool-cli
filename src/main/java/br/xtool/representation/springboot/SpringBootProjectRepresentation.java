package br.xtool.representation.springboot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import br.xtool.representation.ProjectRepresentation;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.JavaUnit;

import br.xtool.implementation.representation.JavaPackageRepresentationImpl;
import br.xtool.implementation.representation.PomRepresentationImpl;
import br.xtool.representation.angular.NgProjectRepresentation;
import br.xtool.representation.plantuml.PlantClassDiagramRepresentation;
import strman.Strman;

/**
 * Representação de um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public interface SpringBootProjectRepresentation extends ProjectRepresentation {

	/**
	 * Retorna o nome da classe base. O nome da classe base é o nome da classe que possui a annotation @SpringBootApplication sem o sufixo 'Application'
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
	JavaPackageRepresentation getRootPkg();

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
	SpringBootApplicationPropertiesRepresentation getApplicationProperties();

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
	SortedSet<JpaEntityRepresentation> getEntities();

	/**
	 * Retorna a lista de projetions do projeto.
	 * 
	 * @return
	 */
	SortedSet<SpringBootProjectionRepresentation> getProjections();

	/**
	 * Retorna a lista de specifications do projeto.
	 * 
	 * @return
	 */
	SortedSet<SpringBooSpecificationRepresentation> getSpecifications();

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	SortedSet<SpringBootRepositoryRepresentation> getRepositories();

	/**
	 * Restorna a lista de services.
	 * 
	 * @return
	 */
	SortedSet<SpringBootServiceClassRepresentation> getServices();

	/**
	 * Retorna a lsta de classes rests.
	 * 
	 * @return
	 */
	SortedSet<SpringBootRestClassRepresentation> getRests();

	/**
	 * Retorna o projeto Angular associado ao projeto SpringBoot. Por conveção o projeto angular associado possui o mesmo nome do projeto Spring Boot menos -service
	 * 
	 * @return
	 */
	Optional<NgProjectRepresentation> getAssociatedAngularProject();

	/**
	 * Retorna o diagrama de classe principal do projeto.
	 * 
	 * @return PlantClassDiagramRepresentation com a representação do diagrama de classe.
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
			PomRepresentation ePom = PomRepresentationImpl.of(pomFile);
			if (ePom.getParentVersion().isPresent() && !ePom.isMultiModule()) {
				return ePom.getParentGroupId().get().equals("org.springframework.boot");
			}
		}
		return false;
	}

	/**
	 * Gera um nome de projeto válido.
	 * 
	 * @param commomName
	 * @return
	 */
	static String genProjectName(String commomName) {
		// @formatter:off
		return StringUtils.lowerCase(
				Strman.toKebabCase(
					StringUtils.endsWithIgnoreCase(commomName, "-service") ? 
						commomName : 
						commomName.concat("-service")
						)
				);
		// @formatter:on
	}

	static String genBaseClassName(String projectName) {
		return Strman.toStudlyCase(projectName.endsWith("Application") ? projectName.replace("Application", "") : projectName);

	}

	static JavaPackageRepresentation genRootPackage(String projectName) {
		String packageName = JavaPackageRepresentation.getDefaultPrefix().concat(".").concat(StringUtils.join(StringUtils.split(Strman.toKebabCase(projectName), "-"), "."));
		return JavaPackageRepresentationImpl.of(packageName);
	}

}
