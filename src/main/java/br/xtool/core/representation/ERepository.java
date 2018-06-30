package br.xtool.core.representation;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

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

	@Override
	public int compareTo(ERepository o) {
		return this.getName().compareTo(o.getName());
	}

	public Optional<EEntity> getEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst();
		// @formatter:on
	}

}
