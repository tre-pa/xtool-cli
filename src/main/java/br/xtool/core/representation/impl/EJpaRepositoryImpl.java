package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EJpaProjection;
import br.xtool.core.representation.EJpaRepository;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class EJpaRepositoryImpl extends EJavaInterfaceImpl implements EJpaRepository {

	private EBootProject springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public EJpaRepositoryImpl(EBootProject springBootProject, JavaInterfaceSource javaInterfaceSource) {
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

	@Override
	public EJpaProjection getTargetProjection() {
		// @formatter:off
		return this.springBootProject.getProjections().stream()
				.filter(e -> e.getName().equals(this.getTargetEntity().getName().concat("Projection")))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("O repositório %s não possui projeção associada.", this.getName())));
		// @formatter:on
	}

}
