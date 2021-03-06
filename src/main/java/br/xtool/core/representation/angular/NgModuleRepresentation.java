package br.xtool.core.representation.angular;

import java.util.List;
import java.util.Optional;

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
	 * Retorna a lista de imports do módulo.
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
	 * Retorna a Page associado ao módulo.
	 *
	 * @return
	 */
	Optional<NgPageRepresentation> getAssociatedPage();

}
