package br.xtool.implementation.context;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import br.xtool.implementation.representation.repo.RepositoryRepresentationImpl;
import lombok.extern.slf4j.Slf4j;
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

	private RepositoryRepresentation repository;

	private RepositoryRepresentation workingRepository;

	@Override
	@SneakyThrows
	public RepositoryRepresentation getRepository() {
		return new RepositoryRepresentationImpl(path);
	}

	@Override
	public RepositoryRepresentation getWorkingRepository() {
		return this.workingRepository;
	}

	@Override
	public void setWorkingRepository(RepositoryRepresentation repositoryRepresentation) {
		this.workingRepository = repositoryRepresentation;
	}

}
