package br.xtool.service;

import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * API de serviço do projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface SpringBootProjectService {

	/**
	 * Cria uma nova aplicação Spring Boot.
	 * 
	 * @param name Nome do projeto Spring Boot.
	 */
	SpringBootProjectRepresentation newApp(String name, String description, String version);

	/**
	 * Gera uma entidade JPA baseado em
	 * 
	 * @param plantClass
	 * @return
	 */
	EntityRepresentation genEntity(PlantClassRepresentation plantClass);

	/**
	 * Cria uma inteface de Repository no projeto para a entidade.
	 * 
	 * @param entity Entidade JPA.
	 * @return {@link RepositoryRepresentation}
	 */
	RepositoryRepresentation genRepository(EntityRepresentation entity);

	/**
	 * Cria uma classe de specification. A criação da classe é gerada automaticamente quando a interface de repositório é gerada.
	 * 
	 * @param entity Entidade JPA.
	 * @return
	 */
	SpecificationRepresentation genSpecification(EntityRepresentation entity);

	/**
	 * Cria uma classe de Service no projeto.
	 * 
	 * @param entity Entidade JPA.
	 * @return
	 */
	ServiceClassRepresentation genService(EntityRepresentation entity);

	/**
	 * Cria uma classe Rest no projeto.
	 * 
	 * @param entity Entidade JPA.
	 * @return
	 */
	RestClassRepresentation genRest(EntityRepresentation entity);

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
