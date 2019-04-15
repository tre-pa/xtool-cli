package br.xtool.service;

import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgDetailRepresentation;
import br.xtool.core.representation.angular.NgEditRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgListRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgRoute;
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
	 * Cria um componente List.
	 * 
	 * @param entity Classe JPA.
	 */
	NgListRepresentation genNgList(EntityRepresentation entity, NgModuleRepresentation ngModule);

	/**
	 * Cria um componente de Detail.
	 * 
	 * @param entity
	 * @param ngModule
	 */
	NgDetailRepresentation genNgDetail(EntityRepresentation entity, NgModuleRepresentation ngModule);

	/**
	 * Cria um componente de Edit para uma entidade.
	 * 
	 * @param entity
	 * @param ngModule
	 */
	NgEditRepresentation genNgEdit(EntityRepresentation entity, NgModuleRepresentation ngModule);

	/**
	 * Imprime a lista de componentes de listagem angular.
	 * 
	 * @param project
	 */
	void printNgLists(NgProjectRepresentation project);

	/**
	 * Adiciona uma rota ao módulo.
	 * 
	 * @param module Módulo Angular
	 * @param route  Rota
	 */
	void addRoute(NgModuleRepresentation module, NgRoute route);

	/**
	 * Adiciona um componente ao módulo.
	 * 
	 * @param module    Módulo Angular
	 * @param component Componente Angular
	 */
	void addComponent(NgModuleRepresentation module, NgComponentRepresentation component);
}
