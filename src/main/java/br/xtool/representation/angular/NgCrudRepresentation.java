package br.xtool.representation.angular;

import java.util.List;
import java.util.Optional;

import br.xtool.representation.springboot.JpaEntityRepresentation;

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
	JpaEntityRepresentation getTargetEntity();

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
