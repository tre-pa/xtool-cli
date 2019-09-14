package br.xtool.implementation.representation.repo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.ModuleRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import lombok.Setter;
import lombok.SneakyThrows;

public class ModuleRepresentationImpl implements ModuleRepresentation {

	private Path path;

	private Set<ComponentRepresentation> components;

	@Setter
	private RepositoryRepresentation repository;

	public ModuleRepresentationImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	public String getName() {
		return path.getFileName().toString();
	}

	@Override
	@SneakyThrows
	public Set<ComponentRepresentation> getComponents() {
		if (Objects.isNull(components)) {
			// @formatter:off
			this.components = Files.list(path)
				.filter(Files::isDirectory)
				.filter(p -> Files.exists(p.resolve(ComponentRepresentation.DESCRIPTOR_FILE)))
				.map(ComponentRepresentationImpl::new)
				.peek(cmdRepo -> cmdRepo.setModule(this))
				.collect(Collectors.toSet());
			// @formatter:on
		}
		return this.components;
	}

	@Override
	public RepositoryRepresentation getRepository() {
		return this.repository;
	}

}
