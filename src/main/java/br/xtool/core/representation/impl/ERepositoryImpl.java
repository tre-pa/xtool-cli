package br.xtool.core.representation.impl;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.EPackage;
import br.xtool.core.representation.ERepository;
import br.xtool.core.representation.ESpringBootProject;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class ERepositoryImpl implements ERepository {

	private ESpringBootProject springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public ERepositoryImpl(ESpringBootProject springBootProject, JavaInterfaceSource javaInterfaceSource) {
		super();
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
	public EPackage getPackage() {
		return EPackageImpl.of(this.javaInterfaceSource.getPackage());
	}

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	@Override
	public Optional<EEntity> getTargetEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public int compareTo(ERepository o) {
		return this.getName().compareTo(o.getName());
	}
}
