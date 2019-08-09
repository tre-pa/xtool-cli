package br.xtool.service;

import br.xtool.core.representation.angular.NgCrudRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;

/**
 * API de serviço do projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface AngularService {

	/**
	 * Gera uma nova aplicação Angular baseada em um archetype localizado em src/main/resources/templates/angular
	 * 
	 * @param name       Nome do projeto
	 * @param descrition Descrição do projeto.
	 * @param version    Versão do archetype.
	 */
	void newApp(String name, String descrition, String version);

	/**
	 * Cria uma nova classe Typescript de dominio em src/app/domain
	 *
	 * @param ngProject Projeto Angular
	 * @param entity    classe Jpa
	 * @return classe Typescript
	 */
	NgEntityRepresentation genNgEntity(NgProjectRepresentation ngProject, EntityRepresentation entity);

	/**
	 *
	 * @param ngProject
	 * @param javaEnum
	 * @return
	 */
	void genNgEnum(NgProjectRepresentation ngProject, JavaEnumRepresentation javaEnum);

	/**
	 * Cria uma nova classe Typescript de dominio em src/app/domain
	 *
	 * @param ngProject Projeto Angular
	 * @param entity    classe Jpa
	 * @return classe Typescript
	 */
	void genNgService(NgProjectRepresentation ngProject, EntityRepresentation entity);

	/**
	 *
	 * @param entity
	 * @param ngModule
	 * @param noDetail
	 * @return
	 */
	NgCrudRepresentation genNgCrud(NgProjectRepresentation ngProject, EntityRepresentation entity, NgModuleRepresentation ngModule, boolean noDetail);

	/**
	 * Imprime a lista de componentes de listagem angular.
	 *
	 * @param project
	 */
	void printNgLists(NgProjectRepresentation project);

	/**
	 * Imprime a lista de entidades de domínio angular.
	 *
	 * @param project
	 */
	void printNgEntities(NgProjectRepresentation project);
}
