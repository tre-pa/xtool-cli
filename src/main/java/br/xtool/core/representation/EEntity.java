package br.xtool.core.representation;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import lombok.Getter;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EEntity extends EClass implements Comparable<EEntity> {

	private ESpringBootProject springBootProject;

	@Getter(lazy = true)
	private final SortedSet<EAttribute> attributes = buildAttributes();

	@Getter(lazy = true)
	private final SortedSet<EAssociation> associations = buildAssociations();

	public EEntity(ESpringBootProject springBootProject, JavaClassSource javaClassSource) {
		super(javaClassSource);
		this.springBootProject = springBootProject;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	private SortedSet<EAttribute> buildAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
				.map(f -> new EAttribute(this.springBootProject,this, f))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
	private SortedSet<EAssociation> buildAssociations() {
		SortedSet<EAssociation> associations = new TreeSet<>();
		// @formatter:off
		this.getAttributes().stream()
			.filter(EAttribute::isAssociation)
			.map(EAttribute::getAssociation)
			.forEach(association -> associations.add(association.get()));
		// @formatter:on
		return associations;
	}

	public EAnnotation addAnnotation() {
		return new EAnnotation(this.javaClassSource.addAnnotation());
	}

	@Override
	public int compareTo(EEntity o) {
		return this.getName().compareTo(o.getName());
	}

}
