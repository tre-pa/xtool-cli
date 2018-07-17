package br.xtool.core.representation;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EEntity extends EClass implements Comparable<EEntity> {

	private ESpringBootProject springBootProject;

	public EEntity(ESpringBootProject springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
		this.springBootProject = springBootProject;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	public SortedSet<EAttribute> getAttributes() {
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
	public SortedSet<EAssociation> getAssociations() {
		SortedSet<EAssociation> associations = new TreeSet<>();
		// @formatter:off
		this.getAttributes().stream()
			.filter(EAttribute::isAssociation)
			.map(EAttribute::getAssociation)
			.forEach(association -> associations.add(association.get()));
		// @formatter:on
		return associations;
	}

	@Override
	public int compareTo(EEntity o) {
		return this.getName().compareTo(o.getName());
	}

}
