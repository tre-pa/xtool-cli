package br.xtool.core.representation.impl;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.representation.EWorkspace;

public class EWorkspaceImpl implements EWorkspace {

	private SortedSet<ESBootProject> springBootProjects;

	private SortedSet<ENgProject> angularProjects;

	private EDirectory directory;

	public EWorkspaceImpl(EDirectory directory) {
		super();
		this.directory = directory;
	}

	@Override
	public SortedSet<ESBootProject> getSpringBootProjects() {
		if (Objects.isNull(this.springBootProjects)) {
			// @formatter:off
			this.springBootProjects = this.directory.getChildrenDirectories().stream()
					.filter(dir -> dir.getProjectType().equals(ProjectType.SPRINGBOOT_PROJECT))
					.map(ESBootProjectImpl::of)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.springBootProjects;
	}

	@Override
	public SortedSet<ENgProject> getAngularProjections() {
		return this.angularProjects;
	}

	@Override
	public void refresh() {

	}

}
