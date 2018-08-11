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

	public EJpaEntityImpl(EBootProject springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
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
			.map(fieldSource -> new EJpaAttributeImpl(this, fieldSource))
			.collect(Collectors.toList());
		// @formatter:on
	}

}
