package br.xtool.core.template.impl;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import br.xtool.core.template.Resource;
import lombok.SneakyThrows;

public class ResourceImpl implements Resource {

	private Path rootPath;

	private Path path;

	private VelocityEngine velocityEngine;

	private VelocityContext velocityContext;

	public ResourceImpl(Path rootPath, Path path, VelocityEngine velocityEngine, VelocityContext velocityContext) {
		super();
		this.rootPath = rootPath;
		this.path = path;
		this.velocityEngine = velocityEngine;
		this.velocityContext = velocityContext;
	}

	@Override
	public Path getPath() {
		StringWriter stringWriter = new StringWriter();
		this.velocityEngine.evaluate(this.velocityContext, stringWriter, new String(), StringUtils.removeEnd(this.path.toString(), ".vm"));
		return Paths.get(stringWriter.toString());
	}

	@Override
	@SneakyThrows
	public byte[] read() {
		if (this.path.toString().endsWith(".vm")) {
			StringWriter stringWriter = new StringWriter();
			this.velocityEngine.evaluate(this.velocityContext, stringWriter, new String(), new String(Files.readAllBytes(this.rootPath.resolve(this.path))));
			return stringWriter.toString().getBytes(StandardCharsets.UTF_8);
		}
		return Files.readAllBytes(this.rootPath.resolve(this.path));
	}

}
