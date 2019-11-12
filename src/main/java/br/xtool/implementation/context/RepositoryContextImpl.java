package br.xtool.implementation.context;

import br.xtool.core.Console;
import br.xtool.core.RepositoryContext;
import br.xtool.implementation.representation.repo.RepositoryRepresentationImpl;
import br.xtool.representation.repo.RepositoryRepresentation;
import br.xtool.representation.repo.directive.XDescriptorRepresentation;
import br.xtool.representation.repo.directive.XParamRepresentation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.nio.file.Path;

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

	@Override
	public CommandLine.Model.CommandSpec create(XDescriptorRepresentation descriptor) {
		CommandLine.Model.CommandSpec commandSpec = CommandLine.Model.CommandSpec.create();
		descriptor.getXDef().getXParams().forEach(xparam -> commandSpec.addOption(this.create(xparam)));
		return commandSpec;
	}

	private CommandLine.Model.OptionSpec create(XParamRepresentation param) {
		return CommandLine.Model.OptionSpec.builder(param.getLabel())
				.description(param.getDescription())
				.required(param.isRequired())
				.type(param.getType())
				.build();
	}
}
