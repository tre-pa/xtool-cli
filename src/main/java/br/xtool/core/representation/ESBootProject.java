package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

/**
 * Representação de um projeto Spring Boot
 * 
 * @author jcruz
 *
 */
public interface ESBootProject extends EProject {

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
	ESBootPom getPom();

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	ESBootAppProperties getApplicationProperties();

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
}
