package br.xtool.core.implementation.representation;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JpaRelationshipRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Classe que representa um atributo JPA de uma entidade.
 * 
 * @author jcruz
 *
 */
public class EntityAttributeRepresentationImpl extends JavaFieldRepresentationImpl implements EntityAttributeRepresentation {

	private SpringBootProjectRepresentation springBootProject;

	private EntityRepresentation entitySource;

	public EntityAttributeRepresentationImpl(SpringBootProjectRepresentation springBootProject, EntityRepresentation entitySource,
			FieldSource<JavaClassSource> fieldSource) {
		super(entitySource, fieldSource);
		this.springBootProject = springBootProject;
		this.entitySource = entitySource;
	}

	@Override
	public EntityRepresentation getEntity() {
		return entitySource;
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.springboot.EntityAttributeRepresentation#isId()
	 */
	@Override
	public boolean isId() {
		return this.getRoasterField().hasAnnotation(Id.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaAttribute#isJpaTransient()
	 */
	@Override
	public boolean isJpaTransientField() {
		return this.getRoasterField().hasAnnotation(Transient.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaAttribute#isLob()
	 */
	@Override
	public boolean isLobField() {
		return this.getRoasterField().hasAnnotation(Lob.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.springboot.EntityAttributeRepresentation#
	 * isRelationshipField()
	 */
	@Override
	public boolean isRelationshipField() {
		return this.getRelationship().isPresent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.springboot.EntityAttributeRepresentation#
	 * isRequired()
	 */
	@Override
	public boolean isRequired() {
		if (this.getRoasterField().hasAnnotation(Column.class)) {
			AnnotationSource<JavaClassSource> annColumn = this.getRoasterField().getAnnotation(Column.class);
			if (Objects.nonNull(annColumn.getLiteralValue("nullable"))) {
				return annColumn.getLiteralValue("nullable").equals("false");
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaAttribute#getRelationship()
	 */
	@Override
	public Optional<JpaRelationshipRepresentation> getJpaRelationship() {
		if (this.isEnumField())
			return Optional.empty();
		if (this.isCollectionField()) {
			String entityName = Types.getGenericsTypeParameter(this.getType().getQualifiedNameWithGenerics());
			// @formatter:off
			return this.springBootProject.getEntities().stream()
					.filter(entity -> entity.getName().equals(entityName))
					.map(entityTarget -> new JpaRelationshipRepresentationImpl(this.entitySource, entityTarget, this))
					.map(JpaRelationshipRepresentation.class::cast)
					.findFirst();
			// @formatter:on
		}
		// @formatter:off
		String entityName = this.getType().getName();
		
		return this.springBootProject.getEntities().stream()
				.filter(entity -> entity.getName().equals(entityName))
				.map(entityTarget -> new JpaRelationshipRepresentationImpl(this.entitySource, entityTarget, this))
				.map(JpaRelationshipRepresentation.class::cast)
				.findFirst();
		// @formatter:on
	}

	@Override
	public Integer getColumnLength() {
		if (this.getRoasterField().hasAnnotation(Column.class)) {
			// @formatter:off
			Optional.ofNullable(this.getRoasterField().getAnnotation(Column.class).getLiteralValue("length"))
				.map(Integer::valueOf)
				.orElse(255);
			// @formatter:on
		}
		return 255;
	}

	@Override
	public Optional<String> getMask() {
		// @formatter:off
		return this.fieldSource.getJavaDoc().getTags()
			.stream()
			.filter(docTag -> docTag.getName().equals("@mask"))
			.map(docTag -> docTag.getValue())
			.findAny();
		// @formatter:on
	}

}
