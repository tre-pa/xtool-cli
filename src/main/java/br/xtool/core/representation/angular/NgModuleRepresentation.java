package br.xtool.core.representation.angular;

import java.util.List;

/**
 * Representação de um módulo Angular
 * 
 * @author jcruz
 *
 */
public interface NgModuleRepresentation extends NgClassRepresentation {

	public static final String ROUTE_PATTERN = "const\\s+routes\\s*:\\s*Routes\\s*=\\s*";

	public static final String DECLARATION_PATTERN = "declarations\\s*:\\s*";

	/**
	 * Retorna a representação do projeto Angular.
	 * 
	 * @return
	 */
	NgProjectRepresentation getProject();

	/**
	 * 
	 * @return
	 */
	List<NgImportRepresentation> getImports();

	/**
	 * Retorna as rotas do módulo.
	 * 
	 * @return
	 */
	List<NgRoute> getRoutes();

	/**
	 * Lista de declarations do módulo.
	 * 
	 * @return
	 */
	List<String> getModuleDeclarations();

	/**
	 * Adiciona um componente a declaração de módulos.
	 * 
	 * @param ngComponent
	 */
	@Deprecated
	void addComponent(NgComponentRepresentation ngComponent);

}
