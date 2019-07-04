package br.xtool.service;

import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;

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
	 * @param name
	 * @param description
	 * @param version
	 * @return
	 */
	SpringBootNgProjectRepresentation newApp(String name, String description, String version);
}
