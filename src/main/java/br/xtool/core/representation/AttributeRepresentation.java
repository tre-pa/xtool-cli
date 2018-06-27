package br.xtool.core.representation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.updater.Updatable;

/**
 * Classe que representa um atributo de classe.
 * 
 * @author jcruz
 *
 */
public class AttributeRepresentation implements Comparable<AttributeRepresentation>, Updatable<FieldSource<JavaClassSource>> {

	private SpringBootProjectRepresentation springBootProject;

	private EntityRepresentation entitySource;

	private FieldSource<JavaClassSource> fieldSource;

	public AttributeRepresentation(SpringBootProjectRepresentation springBootProject, EntityRepresentation entitySource, FieldSource<JavaClassSource> fieldSource) {
		super();
		this.springBootProject = springBootProject;
		this.entitySource = entitySource;
		this.fieldSource = fieldSource;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	public String getName() {
		return this.fieldSource.getName();
	}

	/**
	 * Retorna as annotation do atributo.
	 * 
	 * @return
	 */
	public List<AnnotationSource<JavaClassSource>> getAnnotations() {
		return this.fieldSource.getAnnotations();
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}

	public boolean isAssociation() {
		if (this.isCollection()) {
			// @formatter:off
			return this.springBootProject.getEntities().parallelStream()
					.anyMatch(entity -> this.getType().getTypeArguments().stream()
												.anyMatch(t -> t.getName().equals(entity.getName())));
			// @formatter:on
		}
		// @formatter:off
		return this.springBootProject.getEntities().parallelStream()
				.anyMatch(entity -> entity.getName().equals(this.getType().getName()));
		// @formatter:on
	}

	/**
	 * Verifica se o atributo é uma coleção
	 * 
	 * @return
	 */
	public boolean isCollection() {
		return Stream.of("List", "Set", "Collection").anyMatch(type -> type.equals(this.getType().getName()));
	}

	/**
	 * Verifica se o atributo é static.
	 * 
	 * @return
	 */
	public boolean isStatic() {
		return this.fieldSource.isStatic();
	}

	public boolean isJpaTransient() {
		return this.hasAnnotation("Transient");
	}

	public boolean isJpaLob() {
		return this.hasAnnotation("Lob");
	}

	public void setStringInitialize(String value) {
		this.fieldSource.setStringInitializer(value);
	}

	public void setLiteralInitialize(String value) {
		this.fieldSource.setLiteralInitializer(value);
	}

	public Optional<AssociationRepresentation> getAssociation() {
		if (this.isAssociation()) {
			if (this.isCollection()) {
				// @formatter:off
				return this.springBootProject.getEntities().stream()
						.filter(entity -> this.getType().getTypeArguments().stream()
								.anyMatch(type -> type.getName().equals(entity.getName())))
						.map(entityTarget -> new AssociationRepresentation(entitySource, entityTarget, this))
						.findFirst();
				// @formatter:on
			}
			// @formatter:off
			return this.springBootProject.getEntities().stream()
					.filter(entity -> entity.getName().equals(this.getType().getName()))
					.map(entityTarget -> new AssociationRepresentation(entitySource, entityTarget, this))
					.findFirst();
			// @formatter:on
		}
		return Optional.empty();
	}

	public boolean hasAnnotation(String name) {
		return this.fieldSource.hasAnnotation(name);
	}

	@Override
	public int compareTo(AttributeRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public FieldSource<JavaClassSource> getSource() {
		return this.fieldSource;
	}

}
