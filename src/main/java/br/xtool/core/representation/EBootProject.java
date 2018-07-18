package br.xtool.core.representation;

import java.util.Optional;
import java.util.SortedSet;

import br.xtool.core.representation.angular.ENgProject;

/**
 * Classe que representa um projeto Spring Boot
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
	EClass getMainclass();

	/**
	 * Retorna o pacote raiz.
	 * 
	 * @return
	 */
	EPackage getRootPackage();

	/**
	 * Retorna a representação do arquivo pom.xml
	 * 
	 * @return
	 */
	EPom getPom();

	/**
	 * Retorna a representação do arquivo application.properties
	 * 
	 * @return
	 */
	EAppProperties getApplicationProperties();

	/**
	 * Retorna a lista das entidades JPA do projeto
	 * 
	 * @return
	 */
	SortedSet<EEntity> getEntities();

	/**
	 * Retorna a lista de repositórios.
	 * 
	 * @return
	 */
	SortedSet<ERepository> getRepositories();

	/**
	 * 
	 * @return
	 */
	SortedSet<ERest> getRests();

	Optional<ENgProject> getAssociatedAngularProject();

	String getMainDir();

	void refresh();
}
