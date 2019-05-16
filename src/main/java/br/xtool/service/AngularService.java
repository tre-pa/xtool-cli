package br.xtool.service;

import br.xtool.core.representation.angular.NgCrudRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;

public interface AngularService {

	void newApp(String name, String descrition, String version);

	/**
	 * Cria uma nova classe Typescript de dominio em src/app/domain
	 *
	 * @param ngProject Projeto Angular
	 * @param entity    classe Jpa
	 * @return classe Typescript
	 */
	NgEntityRepresentation genNgEntity(EntityRepresentation entity);

	/**
	 *
	 * @param ngProject
	 * @param javaEnum
	 * @return
	 */
	NgEnumRepresentation genNgEnum(JavaEnumRepresentation javaEnum);

	/**
	 * Cria uma nova classe Typescript de dominio em src/app/domain
	 *
	 * @param ngProject Projeto Angular
	 * @param entity    classe Jpa
	 * @return classe Typescript
	 */
	NgServiceRepresentation genNgService(EntityRepresentation entity);

	/**
	 *
	 * @param entity
	 * @param ngModule
	 * @return
	 */
	NgCrudRepresentation genNgCrud(EntityRepresentation entity, NgModuleRepresentation ngModule);

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
