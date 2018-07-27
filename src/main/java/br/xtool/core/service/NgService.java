package br.xtool.core.service;

import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDialog;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgPage;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.ENgService;
import br.xtool.core.representation.EProject;
import lombok.NonNull;

public interface NgService {

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
	ENgPage createNgPage(ENgProject ngProject, EProject.Version version, ENgModule ngModule, String name);
}
