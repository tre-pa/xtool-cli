package br.xtool.core.representation.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EJpaRelationship;
import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EBootProject;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EJpaEntityImpl extends EJavaClassImpl implements EJpaEntity {

	private EBootProject springBootProject;

	public EJpaEntityImpl(EBootProject springBootProject, JavaClassSource javaClassSource) {
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
	public SortedSet<EJpaAttribute> getAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
			.map(f -> new EJpaAttributeImpl(this.springBootProject,this, f))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
	@Override
	public Set<EJpaRelationship> getRelationships() {
		Set<EJpaRelationship> associations = new HashSet<>();
		// @formatter:off
		this.getAttributes().stream()
			.filter(EJpaAttribute::isAssociation)
			.map(EJpaAttribute::getRelationship)
			.forEach(association -> associations.add(association.get()));
		// @formatter:on
		return associations;
	}

}
