package br.xtool.core.implementation.representation;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;

import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
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
		return javaClassSource.getFields().stream()
				.filter(fieldSource -> !fieldSource.isStatic())
				.map(fieldSource -> new EntityAttributeRepresentationImpl(getProject(),this, fieldSource))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Optional<RepositoryRepresentation> getAssociatedRepository() {
		// @formatter:off
		return getProject().getRepositories().stream()
				.filter(repository -> repository.getName().equals(getName().concat("Repository")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public Optional<SpecificationRepresentation> getAssociatedSpecification() {
		// @formatter:off
		return getProject().getSpecifications().stream()
				.filter(repository -> repository.getName().equals(getName().concat("Specification")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public Optional<ServiceClassRepresentation> getAssociatedService() {
		// @formatter:off
		return getProject().getServices().stream()
				.filter(service -> service.getName().equals(getName().concat("Service")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public Optional<RestClassRepresentation> getAssociatedRest() {
		// @formatter:off
		return getProject().getRests().stream()
				.filter(rest -> rest.getName().equals(getName().concat("Rest")))
				.findFirst();
		// @formatter:on
	}

	@Override
	public Optional<NgEntityRepresentation> getAssociatedNgEntity() {
		if (getProject().getAssociatedAngularProject().isPresent()) {
			NgProjectRepresentation ngProject = getProject().getAssociatedAngularProject().get();
			// @formatter:off
			return ngProject.getNgEntities().stream()
					.filter(ngEntity -> ngEntity.getName().equals(getName()))
					.findAny();
			// @formatter:on
		}
		return Optional.empty();
	}

	@Override
	public Optional<NgServiceRepresentation> getAssociatedNgService() {
		if (getProject().getAssociatedAngularProject().isPresent()) {
			NgProjectRepresentation ngProject = getProject().getAssociatedAngularProject().get();
			// @formatter:off
			return ngProject.getNgServices().stream()
					.filter(ngService -> ngService.getName().equals(getName().concat("Service")))
					.findAny();
			// @formatter:on
		}
		return Optional.empty();
	}

	@Override
	public String asDatabaseTableName() {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
						Strman.toSnakeCase(getName())), "", 30);
		// @formatter:on
	}

	@Override
	public String asDatabaseSequenceName() {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
						"SEQ_" + Strman.toSnakeCase(getName())), "", 30);
		// @formatter:on
	}

	@Override
	public String asDatabaseFkName() {
		// @formatter:off
		return StringUtils.abbreviate(
				StringUtils.upperCase(
						Strman.toSnakeCase(getName())), "", 30) + "_ID";
		// @formatter:on
	}

	@Override
	public JavaDocSource<JavaClassSource> addTagValue(String tagName, String value) {
		if(this.getRoasterJavaClass().getJavaDoc().getTagNames().stream().noneMatch(_tagName -> tagName.equals(tagName))) {
			return this.getRoasterJavaClass().getJavaDoc().addTagValue(tagName, value);
		}
		return null;
	}

}
