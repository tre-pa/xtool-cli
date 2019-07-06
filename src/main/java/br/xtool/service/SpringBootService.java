package br.xtool.service;

import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * API de serviço do projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface SpringBootService {

	/**
	 * Cria uma nova aplicação Spring Boot.
	 * 
	 * @param name Nome do projeto Spring Boot.
	 */
	SpringBootProjectRepresentation newApp(String name, String description, String version);

	/**
	 * Cria uma aplicação Spring Boot e Angular Multi-Módulo.
	 * 
	 * @param name        Nome do projeto
	 * @param description Descrição do projeto
	 * @param version     Versão do projeto
	 * @return {@link SpringBootNgProjectRepresentation}
	 */
	SpringBootNgProjectRepresentation newAppModular(String name, String description, String version);

	/**
	 * Gera uma entidade JPA baseado em
	 * 
	 * @param plantClass
	 * @return
	 */
	EntityRepresentation genEntity(SpringBootProjectRepresentation springBootProject, PlantClassRepresentation plantClass);

	/**
	 * Cria uma inteface de Repository no projeto para a entidade.
	 * 
	 * @param entity Entidade JPA.
	 * @return {@link RepositoryRepresentation}
	 */
	RepositoryRepresentation genRepository(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity);

	/**
	 * Cria uma classe de specification. A criação da classe é gerada automaticamente quando a interface de repositório é gerada.
	 * 
	 * @param entity Entidade JPA.
	 * @return
	 */
	SpecificationRepresentation genSpecification(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity);

	/**
	 * Cria uma classe de Service no projeto.
	 * 
	 * @param entity Entidade JPA.
	 * @return
	 */
	ServiceClassRepresentation genService(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity);

	/**
	 * Cria uma classe Rest no projeto.
	 * 
	 * @param entity Entidade JPA.
	 * @return
	 */
	RestClassRepresentation genRest(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity);

	/**
	 * Imprime a lista as entidades do projeto.
	 * 
	 * @return
	 */
	void printEntities(SpringBootProjectRepresentation project);

	/**
	 * Imprime a lista de Repositórios do projeto.
	 * 
	 * @param project
	 */
	void printRepositories(SpringBootProjectRepresentation project);

	/**
	 * Imprime a lista de Services do projeto.
	 * 
	 * @param project
	 */
	void printServices(SpringBootProjectRepresentation project);

	/**
	 * Imprime a lista de Rests do projeto.
	 * 
	 * @param project
	 */
	void printRests(SpringBootProjectRepresentation project);

}
