package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootRepository;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJpaEntity;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class EBootRepositoryImpl extends EJavaInterfaceImpl implements EBootRepository {

	private EBootProject springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public EBootRepositoryImpl(EBootProject springBootProject, JavaInterfaceSource javaInterfaceSource) {
		super(springBootProject, javaInterfaceSource);
		this.springBootProject = springBootProject;
		this.javaInterfaceSource = javaInterfaceSource;
	}

	/**
	 * Nome do Repository
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.javaInterfaceSource.getName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	@Override
	public EJavaPackage getJavaPackage() {
		return EJavaPackageImpl.of(this.javaInterfaceSource.getPackage());
	}

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	@Override
	public EJpaEntity getTargetEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui entidade JPA associada.", this.getName())));
		// @formatter:on
	}
}
