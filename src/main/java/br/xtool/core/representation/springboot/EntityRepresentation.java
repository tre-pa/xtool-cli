package br.xtool.core.representation.springboot;

import java.util.Collection;
import java.util.Optional;

/**
 * Representação de uma entidade java JPA.
 * 
 * @author jcruz
 *
 */
public interface EntityRepresentation extends JavaClassRepresentation {

	/**
	 * Retorna os atributos JPA da entidade.
	 * 
	 * @return
	 */
	Collection<EntityAttributeRepresentation> getAttributes();

	/**
	 * Retorna os atributos simples (não relacionamento e não enums) da entidade
	 * 
	 * @return
	 */
	@Deprecated
	Collection<EntityAttributeRepresentation> getSimpleAttributes();

	/**
	 * Retorna os atributos que são relacionamentos.
	 * 
	 * @return
	 */
	@Deprecated
	Collection<EntityAttributeRepresentation> getRelationshipAttributes();

	/**
	 * Retorna os relacionamentos 'para muitos'
	 * 
	 * @return
	 */
	@Deprecated
	Collection<EntityAttributeRepresentation> getToManyRelationshipAttributes();

	/**
	 * Retorna os relacionamentos 'para um'
	 * 
	 * @return
	 */
	@Deprecated
	Collection<EntityAttributeRepresentation> getToOneRelationshipAttributes();

	/**
	 * Retorna os atributos que são do tipo enum.
	 * 
	 * @return
	 */
	@Deprecated
	Collection<EntityAttributeRepresentation> getEnumAttributes();

	/**
	 * Retorna o repositório associado a entidade.
	 * 
	 * @return
	 */
	Optional<RepositoryRepresentation> getAssociatedRepository();

	/**
	 * Retorna a specification associada a entidade.
	 * 
	 * @return
	 */
	Optional<SpecificationRepresentation> getAssociatedSpecification();

	/**
	 * Retorna a classe de serviço associado a entidade.
	 * 
	 * @return
	 */
	Optional<ServiceClassRepresentation> getAssociatedServiceClass();

	/**
	 * Retorna a classe rest associado a entidade.
	 * 
	 * @return
	 */
	Optional<RestClassRepresentation> getAssociatedRestClass();

	/**
	 * Retorna um nome válido de uma tabela do banco de dados com no máximo 30 carateres (Limite do oracle).
	 * 
	 * @return
	 */
	String asDatabaseTableName();

	/**
	 * Retorna um nome válido de sequence do banco de dados.
	 * 
	 * @return
	 */
	String asDatabaseSequenceName();

	/**
	 * 
	 * @return
	 */
	String asDatabaseFkName();

}