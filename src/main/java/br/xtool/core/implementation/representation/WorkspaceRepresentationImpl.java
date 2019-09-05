package br.xtool.core.implementation.representation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.WorkspaceRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootFullStackProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import lombok.SneakyThrows;

public class WorkspaceRepresentationImpl implements WorkspaceRepresentation {

	private SortedSet<SpringBootProjectRepresentation> springBootProjects;

	private SortedSet<NgProjectRepresentation> angularProjects;

	private SortedSet<SpringBootFullStackProjectRepresentation> springBootNgProjects;

	private Path path;

	public WorkspaceRepresentationImpl(Path path) {
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
					.map(SpringBootProjectRepresentationImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.springBootProjects;
	}

	@Override
	@SneakyThrows
	public SortedSet<NgProjectRepresentation> getAngularProjects() {
		if (Objects.isNull(this.angularProjects)) {
			// @formatter:off
			this.angularProjects = Files.list(this.path)
					.filter(Files::isDirectory)
					.filter(NgProjectRepresentation::isValid)
					.map(NgProjectRepresentationImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.angularProjects;
	}

	@Override
	@SneakyThrows
	public SortedSet<SpringBootFullStackProjectRepresentation> getSpringBootNgProjects() {
		if (Objects.isNull(this.springBootNgProjects)) {
			// @formatter:off
			this.springBootNgProjects = Files.list(this.path)
					.filter(Files::isDirectory)
					.filter(SpringBootFullStackProjectRepresentation::isValid)
					.map(SpringBootFullStackProjectRepresentationImpl::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.springBootNgProjects;
	}

	@Override
	public SortedSet<? extends ProjectRepresentation> getProjects() {
		return Sets.newTreeSet(Iterables.concat(this.getSpringBootProjects(), this.getAngularProjects(), this.getSpringBootNgProjects()));
//		// @formatter:off
//		return Stream.concat(
//				this.getSpringBootProjects().stream(), 
//				this.getAngularProjects().stream())
//			.collect(Collectors.toCollection(TreeSet::new));
//		// @formatter:on
	}

	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	public void refresh() {

	}

}
