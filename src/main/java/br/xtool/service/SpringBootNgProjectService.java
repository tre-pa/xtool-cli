package br.xtool.service;

import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * API de serviço de projetos multi-módulo Spring Boot e Angular.
 * 
 * @author jcruz
 *
 */
public interface SpringBootNgProjectService {

	/**
	 * Cria uma aplicação Spring Boot e Angular Multi-Módulo.
	 * 
	 * @param name        Nome do projeto
	 * @param description Descrição do projeto
	 * @param version     Versão do projeto
	 * @return {@link SpringBootNgProjectRepresentation}
	 */
	SpringBootNgProjectRepresentation newApp(String name, String description, String version);

	/**
	 * Gera uma entidade JPA baseado em uma classe do diagrama.
	 * 
	 * @param springBootProject
	 * @param plantClass
	 * @return
	 */
	EntityRepresentation genEntity(SpringBootProjectRepresentation springBootProject, PlantClassRepresentation plantClass);
}
