package br.xtool.core.representation.angular;

import java.util.List;
import java.util.Optional;

import br.xtool.core.representation.springboot.EntityRepresentation;

/**
 * Classe que representa um CRUD angular (List, Edit e Detail).
 *
 * @author jcruz
 *
 */
public interface NgCrudRepresentation {

	/**
	 * Retorna a entidade JPA alvo.
	 * 
	 * @return
	 */
	EntityRepresentation getTargetEntity();

	/**
	 * Retorna a representação do List.
	 *
	 * @return
	 */
	NgListRepresentation getList();

	/**
	 * Retorna a representação do Detail.
	 *
	 * @return
	 */
	NgDetailRepresentation getDetail();

	/**
	 * Retorna a representação do Edit.
	 *
	 * @return
	 */
	Optional<NgEditRepresentation> getEdit();

	/**
	 *
	 * @return
	 */
	List<NgRoute> genRoute();

}
