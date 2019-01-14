package br.xtool.core.implementation;

import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgDialogRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgPageRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import lombok.NonNull;

/**
 * Serviços para projetos Angular.
 * 
 * @author jcruz
 *
 */
@Deprecated
public interface NgProject {

	/**
	 * 
	 * @param ngModule
	 * @param ngComponent
	 */
	<T extends NgComponentRepresentation> void addDeclarationToModule(NgProjectRepresentation ngProject, @NonNull NgModuleRepresentation ngModule, @NonNull T ngComponent);

	/**
	 * 
	 * Adiciona uma classe Service ao modulo correspondente.
	 * 
	 * @param ngModule
	 * @param ngService
	 */
	void addProviderToModule(NgProjectRepresentation ngProject, @NonNull NgModuleRepresentation ngModule, @NonNull NgServiceRepresentation ngService);

	/**
	 * 
	 * @param ngModule
	 * @param ngDialog
	 */
	void addEntryComponentsToModule(NgProjectRepresentation ngProject, @NonNull NgModuleRepresentation ngModule, @NonNull NgDialogRepresentation ngDialog);

	/**
	 * Cria uma página no projeto.
	 * 
	 * @param ngProject
	 * @return
	 */
	NgPageRepresentation createNgPage(NgProjectRepresentation ngProject, NgModuleRepresentation ngModule, String name);

	/**
	 * Cria uma class typescript baseada em uma entidade JPA.
	 * 
	 * @param ngProject
	 * @param entity
	 * @return
	 */
	NgClassRepresentation createNgEntity(NgProjectRepresentation ngProject, EntityRepresentation entity);
	
	/**
	 * Cria um Enum typescript baseado em um enum Java.
	 * 
	 * @param ngProject
	 * @param javaEnum
	 * @return
	 */
	NgEnumRepresentation createNgEnum(NgProjectRepresentation ngProject, JavaEnumRepresentation javaEnum);
}
