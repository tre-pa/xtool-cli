package br.xtool.core.representation.impl;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.EJavaEntity;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJavaRepository;
import br.xtool.core.representation.EBootProject;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class EJavaRepositoryImpl implements EJavaRepository {

	private EBootProject springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public EJavaRepositoryImpl(EBootProject springBootProject, JavaInterfaceSource javaInterfaceSource) {
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
	public EJavaPackage getPackage() {
		return EJavaPackageImpl.of(this.javaInterfaceSource.getPackage());
	}

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
	@Override
	public Optional<EJavaEntity> getTargetEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public int compareTo(EJavaRepository o) {
		return this.getName().compareTo(o.getName());
	}
}
