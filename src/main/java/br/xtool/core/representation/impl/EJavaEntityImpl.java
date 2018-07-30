package br.xtool.core.representation.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EJavaRelationship;
import br.xtool.core.representation.EJavaAttribute;
import br.xtool.core.representation.EJavaEntity;
import br.xtool.core.representation.EBootProject;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EJavaEntityImpl extends EJavaClassImpl implements EJavaEntity {

	private EBootProject springBootProject;

	public EJavaEntityImpl(EBootProject springBootProject, JavaClassSource javaClassSource) {
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
	public SortedSet<EJavaAttribute> getAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
			.map(f -> new EJavaAttributeImpl(this.springBootProject,this, f))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
	@Override
	public Set<EJavaRelationship> getRelationships() {
		Set<EJavaRelationship> associations = new HashSet<>();
		// @formatter:off
		this.getAttributes().stream()
			.filter(EJavaAttribute::isAssociation)
			.map(EJavaAttribute::getRelationship)
			.forEach(association -> associations.add(association.get()));
		// @formatter:on
		return associations;
	}

}
