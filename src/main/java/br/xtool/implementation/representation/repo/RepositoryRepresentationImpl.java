package br.xtool.implementation.representation.repo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import br.xtool.representation.repo.ModuleRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import lombok.SneakyThrows;

public class RepositoryRepresentationImpl implements RepositoryRepresentation {

	private Path path;

	private Set<ModuleRepresentation> modules;

	public RepositoryRepresentationImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	@SneakyThrows
	public Set<ModuleRepresentation> getModules() {
		if (Objects.isNull(modules)) {
			// @formatter:off
			this.modules = Files.list(path)
					.filter(Files::isDirectory)
					.map(modulePath -> new ModuleRepresentationImpl(modulePath, this))
					.collect(Collectors.toSet());
			// @formatter:on
		}
		return this.modules;
	}

}
