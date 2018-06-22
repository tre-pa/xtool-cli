package br.xtool.core.model;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

public class Repository implements Comparable<Repository> {

	private JavaInterfaceSource javaInterfaceSource;

	public Repository(JavaInterfaceSource javaInterfaceSource) {
		super();
		this.javaInterfaceSource = javaInterfaceSource;
	}

	public String getName() {
		return this.javaInterfaceSource.getName();
	}

	@Override
	public int compareTo(Repository o) {
		return this.getName().compareTo(o.getName());
	}

}
