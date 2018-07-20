package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.representation.EWorkspace;

public class EWorkspaceImpl implements EWorkspace {

	private Collection<ESBootProject> springBootProjects;

	private Collection<ENgProject> angularProjects;

	private EDirectory directory;

	public EWorkspaceImpl(EDirectory directory) {
		super();
		this.directory = directory;
	}

	@Override
	public Collection<ESBootProject> getSpringBootProjects() {
		if (Objects.isNull(this.springBootProjects)) {
			// @formatter:off
			this.springBootProjects = this.directory.getChildrenDirectories().stream()
					.filter(dir -> dir.getProjectType().equals(ProjectType.SPRINGBOOT1_PROJECT) || dir.getProjectType().equals(ProjectType.SPRINGBOOT2_PROJECT))
					.map(ESBootProjectImpl::of)
					.collect(Collectors.toList());
			// @formatter:on
		}
		return this.springBootProjects;
	}

	@Override
	public Collection<ENgProject> getAngularProjections() {
		return this.angularProjects;
	}

	@Override
	public void refresh() {

	}

}
