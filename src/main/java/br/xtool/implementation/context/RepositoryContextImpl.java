package br.xtool.implementation.context;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import br.xtool.implementation.representation.repo.RepositoryRepresentationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.xtool.core.Console;
import br.xtool.core.RepositoryContext;
import br.xtool.representation.repo.RepositoryRepresentation;
import lombok.SneakyThrows;

@Service
public class RepositoryContextImpl implements RepositoryContext {

	@Value("${repository.home}")
	private Path path;

	@Autowired
	private Console console;

	private Set<RepositoryRepresentation> repositories;

	@PostConstruct
	private void init() {
		// @formatter:off
		long qReps = this.getRepositories().size();
		long qMods = this.getRepositories().stream()
				.flatMap(repo -> repo.getModules().stream())
				.count();
		long qCmps = this.getRepositories().stream()
				.flatMap(repo -> repo.getModules().stream())
				.flatMap(mod -> mod.getComponents().stream())
				.count();
		// @formatter:on
		console.println(String.format("[@|cyan %d|@] Repositório(s), [@|cyan %d|@] Módulo(s), [@|cyan %d|@] Componente(s)", qReps, qMods, qCmps));
	}

	@Override
	@SneakyThrows
	public Set<RepositoryRepresentation> getRepositories() {
		if (Objects.isNull(repositories)) {
			// @formatter:off
			this.repositories = Files.list(path)
					.filter(Files::isDirectory)
					.map(RepositoryRepresentationImpl::new)
					.collect(Collectors.toSet());
			// @formatter:on
		}
		return this.repositories;
	}

}
