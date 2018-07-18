package br.xtool.core.representation.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EAssociation;
import br.xtool.core.representation.EAttribute;
import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.ESpringBootProject;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EEntityImpl extends EClassImpl implements EEntity {

	private ESpringBootProject springBootProject;

	public EEntityImpl(ESpringBootProject springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
		this.springBootProject = springBootProject;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	@Override
	public SortedSet<EAttribute> getAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
			.map(f -> new EAttributeImpl(this.springBootProject,this, f))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
	@Override
	public Set<EAssociation> getAssociations() {
		Set<EAssociation> associations = new HashSet<>();
		// @formatter:off
		this.getAttributes().stream()
			.filter(EAttribute::isAssociation)
			.map(EAttribute::getAssociation)
			.forEach(association -> associations.add(association.get()));
		// @formatter:on
		return associations;
	}

}
