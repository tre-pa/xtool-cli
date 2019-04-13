package br.xtool.service;

import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaPackageRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public interface SpringBootService {

	/**
	 * Cria uma nova aplicação Spring Boot.
	 * 
	 * @param name Nome do projeto Spring Boot.
	 */
	SpringBootProjectRepresentation newApp(String name, String description, String version);

	/**
	 * 
	 * @param plantClass
	 * @return
	 */
	EntityRepresentation genEntity(PlantClassRepresentation plantClass);

	/**
	 * Cria uma inteface de Repository no projeto para a entidade.
	 * 
	 * @param entity
	 * @return
	 */
	RepositoryRepresentation genRepository(EntityRepresentation entity);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	SpecificationRepresentation genSpecification(EntityRepresentation entity);

	/**
	 * Cria uma classe de Service no projeto.
	 * 
	 * @param repository Repositório selecionado.
	 * @return
	 */
	ServiceClassRepresentation genService(EntityRepresentation entity);

	/**
	 * Cria uma classe Rest no projeto.
	 * 
	 * @param repository Repositório selecionado.
	 * @return
	 */
	RestClassRepresentation genRest(EntityRepresentation entity);

	/**
	 * Gera um nome de projeto válido.
	 * 
	 * @param commomName
	 * @return
	 */
	String genProjectName(String commomName);

	/**
	 * Gera um nome de classe base (Classe main) valido.
	 * 
	 * @param projectName
	 * @return
	 */
	String genBaseClassName(String projectName);

	/**
	 * Gera um nome de pacote base válido.
	 * 
	 * @param projectName
	 * @return
	 */
	JavaPackageRepresentation genRootPackage(String projectName);

}
