package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

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
	 * 
	 * @return
	 */
	SortedSet<EJavaRest> getRests();

	Optional<ENgProject> getAssociatedAngularProject();

	@Override
	String getMainDir();

	@Override
	void refresh();
}
