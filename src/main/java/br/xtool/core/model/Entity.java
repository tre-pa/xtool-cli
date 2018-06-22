package br.xtool.core.model;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class Entity implements Comparable<Entity> {

	private SpringBootProject springBootProject;

	private JavaClassSource javaClassSource;

	private SortedSet<Attribute> attributes;

	private SortedSet<Attribute> singleAssociations;
	
	private SortedSet<Attribute> collectionAssociations;

	public Entity(SpringBootProject springBootProject, JavaClassSource javaClassSource) {
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
	public Package getPackage() {
		return Package.of(javaClassSource.getPackage());
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
	public SortedSet<Attribute> getAttributes() {
		if (this.attributes == null) {
			// @formatter:off
			this.attributes = this.javaClassSource.getFields().stream()
					.map(Attribute::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.attributes;
	}
	
	public SortedSet<Attribute> getSingleAssociations() {
		if (this.singleAssociations == null) {
			// @formatter:off
			this.singleAssociations = this.getAttributes().stream()
					.filter(attr -> this.springBootProject.getEntities().stream()
						.anyMatch(e -> e.getName().equals(attr.getType().getName())))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.singleAssociations;
	}
	
	public SortedSet<Attribute> getCollectionAssociations() {
		if (this.collectionAssociations == null) {
			// @formatter:off
			this.collectionAssociations = this.getAttributes().stream()
					.filter(attr -> Stream.of("List", "Set", "Collection").anyMatch(type -> type.equals(attr.getType().getName())))
					.filter(attr -> this.springBootProject.getEntities().stream()
						.anyMatch(entity -> attr.getType().getTypeArguments().stream()
								.anyMatch(arg -> arg.getName().equals(entity.getName()))))
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.collectionAssociations;
	}

	@Override
	public int compareTo(Entity o) {
		return this.getName().compareTo(o.getName());
	}

}
