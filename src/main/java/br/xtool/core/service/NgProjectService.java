package br.xtool.core.service;

import br.xtool.core.representation.EJavaEnum;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.ENgClass;
import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDialog;
import br.xtool.core.representation.ENgEnum;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgPage;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.ENgService;
import lombok.NonNull;

/**
 * Serviços para projetos Angular.
 * 
 * @author jcruz
 *
 */
public interface NgProjectService {

	/**
	 * 
	 * @param ngModule
	 * @param ngComponent
	 */
	<T extends ENgComponent> void addDeclarationToModule(ENgProject ngProject, @NonNull ENgModule ngModule, @NonNull T ngComponent);

	/**
	 * 
	 * Adiciona uma classe Service ao modulo correspondente.
	 * 
	 * @param ngModule
	 * @param ngService
	 */
	void addProviderToModule(ENgProject ngProject, @NonNull ENgModule ngModule, @NonNull ENgService ngService);

	/**
	 * 
	 * @param ngModule
	 * @param ngDialog
	 */
	void addEntryComponentsToModule(ENgProject ngProject, @NonNull ENgModule ngModule, @NonNull ENgDialog ngDialog);

	/**
	 * Cria uma página no projeto.
	 * 
	 * @param ngProject
	 * @return
	 */
	ENgPage createNgPage(ENgProject ngProject, ENgModule ngModule, String name);

	/**
	 * Cria uma class typescript baseada em uma entidade JPA.
	 * 
	 * @param ngProject
	 * @param entity
	 * @return
	 */
	ENgClass createNgEntity(ENgProject ngProject, EJpaEntity entity);
	
	/**
	 * Cria um Enum typescript baseado em um enum Java.
	 * 
	 * @param ngProject
	 * @param javaEnum
	 * @return
	 */
	ENgEnum createNgEnum(ENgProject ngProject, EJavaEnum javaEnum);
}
