package br.xtool.implementation.context;

import br.xtool.context.RepositoryContext;
import br.xtool.core.Console;
import br.xtool.implementation.representation.repo.RepositoryRepresentationImpl;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.RepositoryRepresentation;
import br.xtool.representation.repo.directive.DescriptorYmlRepresentation;
import br.xtool.representation.repo.directive.ParamDefRepresentation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepositoryContextImpl implements RepositoryContext {

	@Value("${repository.home}")
	private Path path;

	@Autowired
	private Console console;

	private RepositoryRepresentation repository;

	private RepositoryRepresentation workingRepository;

	@PostConstruct
	private void init() {
		RepositoryRepresentation repository = this.getRepositories().stream()
				.filter(repo -> repo.getName().equals(RepositoryRepresentation.MASTER_REPOSITORY))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("Repositório 'master' não encontrado.")));
		this.setWorkingRepository(repository);
	}

	@Override
	@SneakyThrows
	public List<RepositoryRepresentation> getRepositories() {
		return Files.list(path)
				.map(p -> new RepositoryRepresentationImpl(p))
				.collect(Collectors.toList());
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
	public CommandLine.Model.CommandSpec create(DescriptorYmlRepresentation descriptor) {
		CommandLine.Model.CommandSpec commandSpec = CommandLine.Model.CommandSpec.create();
		descriptor.getComponentDef().getParams().forEach(xparam -> commandSpec.addOption(this.create(xparam)));
		commandSpec.addOption(CommandLine.Model.OptionSpec.builder("--help")
				.usageHelp(true)
				.description("Exibe a ajuda do componente.").build());
		return commandSpec;
	}

	private CommandLine.Model.OptionSpec create(ParamDefRepresentation param) {
		return CommandLine.Model.OptionSpec.builder(param.getLabel())
				.description(param.getDescription())
				.required(param.isRequired())
				.type(param.getType())
				.build();
	}

	@Override
	public Optional<ComponentRepresentation> findComponentByName(String name) {
		return this.getWorkingRepository().getModules()
				.stream()
				.flatMap(module -> module.getComponents().stream())
				.filter(componente -> componente.getName().equals(name))
				.findFirst();
	}

	@Override
	public long getTotalRepositories() {
		return getRepositories().size();
	}
}
