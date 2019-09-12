package br.xtool.core.representation.springboot;

import java.util.Collection;
import java.util.Optional;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;

import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;

/**
 * Representação de uma entidade java JPA.
 *
 * @author jcruz
 *
 */
public interface JpaEntityRepresentation extends JavaClassRepresentation {

	/**
	 * Retorna os atributos JPA da entidade.
	 *
	 * @return
	 */
	Collection<JpaEntityAttributeRepresentation> getAttributes();

	/**
	 * Retorna o repositório associado a entidade.
	 *
	 * @return
	 */
	Optional<SpringBootRepositoryRepresentation> getAssociatedRepository();

	/**
	 * Retorna a specification associada a entidade.
	 *
	 * @return
	 */
	Optional<SpringBooSpecificationRepresentation> getAssociatedSpecification();

	/**
	 * Retorna a classe de serviço associado a entidade.
	 *
	 * @return
	 */
	Optional<SpringBootServiceClassRepresentation> getAssociatedService();

	/**
	 * Retorna a classe rest associado a entidade.
	 *
	 * @return
	 */
	Optional<SpringBootRestClassRepresentation> getAssociatedRest();

	/**
	 * Retorna a classe de entidade angular associadad a entidade.
	 *
	 * @return
	 */
	Optional<NgEntityRepresentation> getAssociatedNgEntity();

	/**
	 * Retorna a classe de serviço angular associado a entidade.
	 *
	 * @return
	 */
	Optional<NgServiceRepresentation> getAssociatedNgService();
	
	/**
	 * Adciona uma doclet a entidade
	 * 
	 */
	JavaDocSource<JavaClassSource> addTagValue(String tagName, String value);

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
