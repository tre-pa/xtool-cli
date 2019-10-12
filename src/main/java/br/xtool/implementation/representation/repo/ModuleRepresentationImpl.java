package br.xtool.implementation.representation.repo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.DescriptorRepresentation;
import br.xtool.representation.repo.ModuleRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import lombok.Setter;
import lombok.SneakyThrows;

public class ModuleRepresentationImpl implements ModuleRepresentation {

	private Path path;

	private RepositoryRepresentation repository;

	public ModuleRepresentationImpl(Path path, RepositoryRepresentation repository) {
		super();
		this.path = path;
		this.repository = repository;
	}

	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	public String getName() {
		return path.getFileName().toString();
	}

	@Override
	@SneakyThrows
	public Set<ComponentRepresentation> getComponents() {
		// @formatter:off
		return Files.list(path)
				.filter(Files::isDirectory)
				.map(cmpPath -> new ComponentRepresentationImpl(cmpPath, this))
				.collect(Collectors.toSet());
		// @formatter:on
	}

	@Override
	public RepositoryRepresentation getRepository() {
		return this.repository;
	}

}
