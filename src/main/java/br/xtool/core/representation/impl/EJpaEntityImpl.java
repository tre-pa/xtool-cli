package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaEntity;

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
	public Collection<EJpaAttribute> getAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
			.filter(fieldSource -> !fieldSource.isStatic())
			.map(f -> new EJpaAttributeImpl(this.springBootProject,this, f))
			.collect(Collectors.toList());
		// @formatter:on
	}

	//	/**
	//	 * Retorna as associações da entidade.
	//	 * 
	//	 * @return
	//	 */
	//	@Override
	//	@Deprecated
	//	public Set<EJpaRelationship> getRelationships() {
	//		Set<EJpaRelationship> associations = new HashSet<>();
//		// @formatter:off
//		this.getAttributes().stream()
//			.filter(EJpaAttribute::isRelationship)
//			.map(EJpaAttribute::getRelationship)
//			.forEach(association -> associations.add(association.get()));
//		// @formatter:on
	//		return associations;
	//	}

}
