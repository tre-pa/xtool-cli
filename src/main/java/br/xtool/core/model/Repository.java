package br.xtool.core.model;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class Repository implements Comparable<Repository> {

	private SpringBootProject springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public Repository(SpringBootProject springBootProject, JavaInterfaceSource javaInterfaceSource) {
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
	public int compareTo(Repository o) {
		return this.getName().compareTo(o.getName());
	}

	public Optional<Entity> getEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst();
		// @formatter:on
	}

}
