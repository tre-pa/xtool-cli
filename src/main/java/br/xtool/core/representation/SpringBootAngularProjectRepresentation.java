package br.xtool.core.representation;

import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Classe que representa um projeto maven multi módulo com o projeto Spring Boot e Angular
 * 
 * @author jcruz
 *
 */
public interface SpringBootAngularProjectRepresentation extends ProjectRepresentation {

	/**
	 * Retorna a referência do projeto SpringBoot
	 * 
	 * @return {@link SpringBootProjectRepresentation}
	 */
	SpringBootProjectRepresentation getSpringBootProject();

	/**
	 * Retorna a referência do projeto Angular
	 * 
	 * @return {@link NgProjectRepresentation}
	 */
	NgProjectRepresentation getAngularProject();
}
