package br.xtool.core.service;

import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDialog;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgService;
import lombok.NonNull;

public interface NgService {

	/**
	 * 
	 * @param ngModule
	 * @param ngComponent
	 */
	public <T extends ENgComponent> void addDeclarationToModule(@NonNull ENgModule ngModule, @NonNull T ngComponent);

	/**
	 * 
	 * @param ngModule
	 * @param ngService
	 */
	public void addProviderToModule(@NonNull ENgModule ngModule, @NonNull ENgService ngService);

	/**
	 * 
	 * @param ngModule
	 * @param ngDialog
	 */
	public void addEntryComponentsToModule(@NonNull ENgModule ngModule, @NonNull ENgDialog ngDialog);
}
