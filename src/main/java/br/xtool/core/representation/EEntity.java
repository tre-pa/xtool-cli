package br.xtool.core.representation;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.impl.EAttributeImpl;
import br.xtool.core.representation.impl.EClassImpl;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EEntity extends EClassImpl {

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
			.map(f -> new EAttributeImpl(this.springBootProject,this, f))
			.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as associações da entidade.
	 * 
	 * @return
	 */
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
