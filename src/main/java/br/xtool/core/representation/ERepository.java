package br.xtool.core.representation;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.xtool.core.representation.impl.EPackageImpl;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class ERepository implements Comparable<ERepository> {

	private ESpringBootProject springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public ERepository(ESpringBootProject springBootProject, JavaInterfaceSource javaInterfaceSource) {
		super();
		this.springBootProject = springBootProject;
		this.javaInterfaceSource = javaInterfaceSource;
	}

	/**
	 * Nome do Repository
	 * 
	 * @return
	 */
	public String getName() {
		return this.javaInterfaceSource.getName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	public EPackage getPackage() {
		return EPackageImpl.of(this.javaInterfaceSource.getPackage());
	}

	/**
	 * Retorna a entidade alvo do repositório.
	 * 
	 * @return
	 */
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
