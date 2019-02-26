package br.xtool.service;

import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;

public interface AngularService {

	void newApp(String name, String version);

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
	void genNgList(EntityRepresentation entity, NgModuleRepresentation ngModule);
}
