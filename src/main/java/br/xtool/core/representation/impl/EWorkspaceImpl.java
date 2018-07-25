package br.xtool.core.representation.impl;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EProject.Type;
import br.xtool.core.representation.EWorkspace;

public class EWorkspaceImpl implements EWorkspace {

	private SortedSet<EBootProject> springBootProjects;

	private SortedSet<ENgProject> angularProjects;

	private EDirectory directory;

	public EWorkspaceImpl(EDirectory directory) {
		super();
		this.directory = directory;
	}

	@Override
	public SortedSet<EBootProject> getSpringBootProjects() {
		if (Objects.isNull(this.springBootProjects)) {
			// @formatter:off
			this.springBootProjects = this.directory.getDirectories().stream()
					.filter(dir -> dir.getProjectType().equals(Type.SPRINGBOOT_PROJECT))
					.map(EDirectory::getPath)
					.map(EBootProjectImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.springBootProjects;
	}

	@Override
	public SortedSet<ENgProject> getAngularProjections() {
		if (Objects.isNull(this.angularProjects)) {
			// @formatter:off
			this.angularProjects = this.directory.getDirectories().stream()
					.filter(dir -> dir.getProjectType().equals(Type.ANGULAR_PROJECT))
					.map(EDirectory::getPath)
					.map(ENgProjectImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.angularProjects;
	}

	@Override
	public SortedSet<EProject> getProjects() {
		SortedSet<EProject> projects = new TreeSet<>();
		projects.addAll(this.getSpringBootProjects());
		projects.addAll(this.getAngularProjections());
		return projects;
	}

	@Override
	public void refresh() {

	}

}
