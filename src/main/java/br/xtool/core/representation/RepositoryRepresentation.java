package br.xtool.core.representation;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 * Classe que representa uma inteface Repository
 * 
 * @author jcruz
 *
 */
public class RepositoryRepresentation implements Comparable<RepositoryRepresentation> {

	private SpringBootProjectRepresentation springBootProject;

	private JavaInterfaceSource javaInterfaceSource;

	public RepositoryRepresentation(SpringBootProjectRepresentation springBootProject, JavaInterfaceSource javaInterfaceSource) {
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
	public int compareTo(RepositoryRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

	public Optional<EntityRepresentation> getEntity() {
		// @formatter:off
		return this.springBootProject.getEntities().stream()
				.filter(e -> e.getName().concat("Repository").equals(this.getName()))
				.findFirst();
		// @formatter:on
	}

}
