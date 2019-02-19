package br.xtool.core.implementation.representation;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.ServiceClassRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import strman.Strman;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EntityRepresentationImpl extends JavaClassRepresentationImpl implements EntityRepresentation {

	public EntityRepresentationImpl(SpringBootProjectRepresentation springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	@Override
	public Collection<EntityAttributeRepresentation> getAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
			.filter(fieldSource -> !fieldSource.isStatic())
			.map(fieldSource -> new EntityAttributeRepresentationImpl(this.getProject(),this, fieldSource))
			.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaEntity#getSimpleAttributes()
	 */
	@Override
	public Collection<EntityAttributeRepresentation> getSimpleAttributes() {
		// @formatter:off
		return this.getAttributes().stream()
				.filter(attr ->  !attr.getJpaRelationship().isPresent())
				.filter(attr -> !attr.getEnum().isPresent())
				.collect(Collectors.toList());
		// @formatter:on

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaEntity#getRelationshipAttributes()
	 */
	@Override
	public Collection<EntityAttributeRepresentation> getRelationshipAttributes() {
		// @formatter:off
		return this.getAttributes().stream()
				.filter(attr -> attr.getJpaRelationship().isPresent())
				.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaEntity#getEnumAttributes()
	 */
	@Override
	public Collection<EntityAttributeRepresentation> getEnumAttributes() {
		// @formatter:off
		return this.getAttributes().stream()
				.filter(attr -> attr.getEnum().isPresent())
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Collection<EntityAttributeRepresentation> getToManyRelationshipAttributes() {
		// @formatter:off
		return this.getRelationshipAttributes().stream()
				.filter(attr -> attr.getRoasterField().hasAnnotation(ManyToMany.class) || attr.getRoasterField().hasAnnotation(OneToMany.class))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Collection<EntityAttributeRepresentation> getToOneRelationshipAttributes() {
		// @formatter:off
		return this.getRelationshipAttributes().stream()
				.filter(attr -> attr.getRoasterField().hasAnnotation(OneToOne.class) || attr.getRoasterField().hasAnnotation(ManyToOne.class))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Optional<RepositoryRepresentation> getAssociatedRepository() {
		// @formatter:off
		return this.getProject().getRepositories().stream()
				.filter(repository -> repository.getName().equals(this.getName().concat("Repository")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public Optional<SpecificationRepresentation> getAssociatedSpecification() {
		// @formatter:off
		return this.getProject().getSpecifications().stream()
				.filter(repository -> repository.getName().equals(this.getName().concat("Specification")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public Optional<ServiceClassRepresentation> getAssociatedServiceClass() {
		// @formatter:off
		return this.getProject().getServices().stream()
				.filter(service -> service.getName().equals(this.getName().concat("Service")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public Optional<RestClassRepresentation> getAssociatedRestClass() {
		// @formatter:off
		return this.getProject().getRests().stream()
				.filter(rest -> rest.getName().equals(this.getName().concat("Rest")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public String asDatabaseTableName() {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
						Strman.toSnakeCase(this.getName())), "", 30);
		// @formatter:on
	}

	@Override
	public String asDatabaseSequenceName() {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
				"SEQ_" + Strman.toSnakeCase(this.getName())), "", 30);
		// @formatter:on
	}

	@Override
	public String asDatabaseFkName() {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
				Strman.toSnakeCase(this.getName())), "", 30) + "_ID";
		// @formatter:on
	}

}
