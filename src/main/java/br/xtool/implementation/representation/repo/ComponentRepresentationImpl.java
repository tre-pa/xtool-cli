package br.xtool.implementation.representation.repo;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

import br.xtool.representation.repo.ModuleRepresentation;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import br.xtool.representation.repo.ComponentRepresentation;
import lombok.SneakyThrows;
import picocli.CommandLine;

public class ComponentRepresentationImpl implements ComponentRepresentation {

	private Path path;

	private Map<String, Object> descriptor;

	@Setter
	private ModuleRepresentation module;

	public ComponentRepresentationImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	public String getName() {
		return path.getFileName().toString();
	}

	@Override
	public String getFullyQualifiedName() {
		return this.getModule().getRepository().getName().concat(this.getModule().getName()).concat(this.getName());
	}

	@Override
	public Path getTplPath() {
		return path.resolve("tpl");
	}
	
	@Override
	@SneakyThrows
	public Map<String, Object> getDescriptor() {
		if (Objects.isNull(descriptor)) {
			InputStream input = Files.newInputStream(path.resolve(ComponentRepresentation.DESCRIPTOR_FILE));
			this.descriptor = new Yaml().load(input);
		}
		return this.descriptor;
	}

	@Override
	public CommandLine.Model.CommandSpec getCommandSpec() {
		CommandLine.Model.CommandSpec cmpCommandSpec = CommandLine.Model.CommandSpec.create();
		cmpCommandSpec.name(String.format("%s@%s:%s", this.getModule().getRepository().getName(), this.getModule().getName(), this.getName()));
		return cmpCommandSpec;
	}

	@Override
	public ModuleRepresentation getModule() {
		return this.module;
	}


}
