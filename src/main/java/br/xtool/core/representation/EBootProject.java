package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.impl.EJavaPackageImpl;
import strman.Strman;

/**
 * Representação de um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public interface EBootProject extends EProject {

	/**
	 * Retorna o nome da classe base. O nome da classe base é o nome da classe que
	 * possui a annotation @SpringBootApplication sem o sufixo 'Application'
	 * 
	 * @return Nome da classe base.
	 */
	String getBaseClassName();

	/**
	 * 
	 * @return
	 */
	EJavaClass getMainclass();

	/**
	 * Retorna o pacote raiz.
	 * 
	 * @return
	 */
	EJavaPackage getRootPackage();

	/**
	 * 
	 * @return
	 */
	EJavaSourceFolder getMainSourceFolder();

	/**
	 * 
	 * @return
	 */
	EJavaSourceFolder getTestSourceFolder();

	/**
	 * Retorna a representação do arquivo pom.xml
	 * 
	 * @return
	 */
	EBootPom getPom();

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	EBootAppProperties getApplicationProperties();

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	SortedSet<EJavaEntity> getEntities();

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	SortedSet<EJavaRepository> getRepositories();

	/**
	 * Retorna a lsta de classes rests.
	 * 
	 * @return
	 */
	SortedSet<EJavaRest> getRests();

	/**
	 * Retorna o projeto Angular associado ao projeto SpringBoot. Por conveção o
	 * projeto angular associado possui o mesmo nome do projeto Spring Boot menos
	 * -service
	 * 
	 * @return
	 */
	Optional<ENgProject> getAssociatedAngularProject();

	/**
	 * Retorna o diretório principal do java.
	 */
	//	@Override
	//	String getMainDir();

	/**
	 * Retorna o diagrama de classe das entidades de domínio.
	 * 
	 * @return
	 */
	Optional<EUmlClassDiagram> getDomainClassDiagram();

	/**
	 * 
	 */
	@Override
	void refresh();

	/**
	 * gera um nome valido de projeto.
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

	/**
	 * Gera um pacote raiz para um nome de projeto.
	 * 
	 * @param projectName
	 * @return
	 */
	static EJavaPackage genRootPackage(String projectName) {
		String packageName = EJavaPackage.getDefaultPrefix().concat(".").concat(StringUtils.join(StringUtils.split(Strman.toKebabCase(projectName), "-"), "."));
		return EJavaPackageImpl.of(packageName);
	}

}