package br.xtool.core.representation.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EWorkspace;
import lombok.SneakyThrows;

public class EWorkspaceImpl implements EWorkspace {

	private SortedSet<EBootProject> springBootProjects;

	private SortedSet<ENgProject> angularProjects;

	private Path path;

	public EWorkspaceImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	@SneakyThrows
	public SortedSet<EBootProject> getSpringBootProjects() {
		if (Objects.isNull(this.springBootProjects)) {
			// @formatter:off
			this.springBootProjects = Files.list(this.path)
					.filter(Files::isDirectory)
					.filter(EBootProject::isValid)
					.map(EBootProjectImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.springBootProjects;
	}

	@Override
	@SneakyThrows
	public SortedSet<ENgProject> getAngularProjections() {
		if (Objects.isNull(this.angularProjects)) {
			// @formatter:off
			this.angularProjects = Files.list(this.path)
					.filter(Files::isDirectory)
					.filter(ENgProject::isValid)
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
