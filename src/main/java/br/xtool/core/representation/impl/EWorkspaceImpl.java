package br.xtool.core.representation.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.WorkspaceRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import lombok.SneakyThrows;

public class EWorkspaceImpl implements WorkspaceRepresentation {

	private SortedSet<SpringBootProjectRepresentation> springBootProjects;

	private SortedSet<NgProjectRepresentation> angularProjects;

	private Path path;

	public EWorkspaceImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	@SneakyThrows
	public SortedSet<SpringBootProjectRepresentation> getSpringBootProjects() {
		if (Objects.isNull(this.springBootProjects)) {
			// @formatter:off
			this.springBootProjects = Files.list(this.path)
					.filter(Files::isDirectory)
					.filter(SpringBootProjectRepresentation::isValid)
					.map(EBootProjectImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.springBootProjects;
	}

	@Override
	@SneakyThrows
	public SortedSet<NgProjectRepresentation> getAngularProjections() {
		if (Objects.isNull(this.angularProjects)) {
			// @formatter:off
			this.angularProjects = Files.list(this.path)
					.filter(Files::isDirectory)
					.filter(NgProjectRepresentation::isValid)
					.map(ENgProjectImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.angularProjects;
	}

	@Override
	public SortedSet<ProjectRepresentation> getProjects() {
		return Stream.concat(this.getSpringBootProjects().stream(), this.getAngularProjections().stream()).collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	public void refresh() {

	}

}
