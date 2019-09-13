package br.xtool.representation.repo;

import java.util.Set;

/**
 * Classe que representa um módulo de componentes.
 * 
 * @author jcruz
 *
 */
public interface ModuleRepresentation {

	/**
	 * Nome do módulo.
	 * 
	 * @return
	 */
	String name();

	/**
	 * Retorna a lista de componentes do módulo.
	 * 
	 * @return
	 */
	Set<ComponentRepresentation> getComponents();
}
