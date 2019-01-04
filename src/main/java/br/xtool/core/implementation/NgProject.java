package br.xtool.core.implementation;

import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.JavaEnumRepresentation;
import br.xtool.core.representation.NgClassRepresentation;
import br.xtool.core.representation.NgComponentRepresentation;
import br.xtool.core.representation.NgDialogRepresentation;
import br.xtool.core.representation.NgEnumRepresentation;
import br.xtool.core.representation.NgModuleRepresentation;
import br.xtool.core.representation.NgPageRepresentation;
import br.xtool.core.representation.NgProjectRepresentation;
import br.xtool.core.representation.NgServiceRepresentation;
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
