package br.xtool.core.representation;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EntityRepresentation implements Comparable<EntityRepresentation> {

	private SpringBootProjectRepresentation springBootProject;

	private JavaClassSource javaClassSource;

	private SortedSet<AttributeRepresentation> attributes;

	private SortedSet<AssociationRepresentation> associations;

	public EntityRepresentation(SpringBootProjectRepresentation springBootProject, JavaClassSource javaClassSource) {
		super();
		this.springBootProject = springBootProject;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Nome da classe
	 * 
	 * @return
	 */
	public String getName() {
		return javaClassSource.getName();
	}

	/**
	 * Retorna o nome qualificado da classe: pacote+class
	 * 
	 * @return
	 */
	public String getQualifiedName() {
		return javaClassSource.getQualifiedName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	public PackageRepresentation getPackage() {
		return PackageRepresentation.of(javaClassSource.getPackage());
	}

	/**
	 * Retorna as annotations da classe
	 * 
	 * @return
	 */
	public List<AnnotationSource<JavaClassSource>> getAnnotations() {
		return this.javaClassSource.getAnnotations();
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	public SortedSet<AttributeRepresentation> getAttributes() {
		if (this.attributes == null) {
			// @formatter:off
			this.attributes = this.javaClassSource.getFields().stream()
					.map(f -> new AttributeRepresentation(this.springBootProject,this, f))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.attributes;
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
	public SortedSet<AssociationRepresentation> getAssociations() {
		if (this.associations == null) {
			this.associations = new TreeSet<>();
			// @formatter:off
			this.getAttributes().stream()
				.filter(AttributeRepresentation::isAssociation)
				.map(AttributeRepresentation::getAssociation)
				.forEach(association -> this.associations.add(association.get()));
			// @formatter:on
		}
		return this.associations;
	}

	/**
	 * Verifica se a entidade possui a annotation
	 * 
	 * @param name
	 *            Nome da annotation
	 * @return
	 */
	public boolean hasAnnotation(String name) {
		return this.javaClassSource.hasAnnotation(name);
	}

	@Override
	public int compareTo(EntityRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

}
