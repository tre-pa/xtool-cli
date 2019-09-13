package br.xtool.implementation.representation.repo;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

import org.yaml.snakeyaml.Yaml;

import br.xtool.representation.repo.ComponentRepresentation;
import lombok.SneakyThrows;

public class ComponentRepresentationImpl implements ComponentRepresentation {

	private Path path;

	private Map<String, Object> descriptor;

	public ComponentRepresentationImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	public String getName() {
		return path.getFileName().toString();
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

}
