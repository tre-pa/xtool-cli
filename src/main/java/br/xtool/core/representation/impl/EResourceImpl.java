package br.xtool.core.representation.impl;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import br.xtool.core.representation.ResourceRepresentation;
import lombok.SneakyThrows;

public class EResourceImpl implements ResourceRepresentation {

	private Path rootPath;

	private Path relativePath;

	private VelocityEngine velocityEngine;

	private VelocityContext velocityContext;

	public EResourceImpl(Path rootPath, Path path, VelocityEngine velocityEngine, VelocityContext velocityContext) {
		super();
		this.rootPath = rootPath;
		this.relativePath = path;
		this.velocityEngine = velocityEngine;
		this.velocityContext = velocityContext;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EResource#getPath()
	 */
	@Override
	public Path getRelativePath() {
		StringWriter stringWriter = new StringWriter();
		this.velocityEngine.evaluate(this.velocityContext, stringWriter, new String(), StringUtils.removeEnd(this.relativePath.toString(), ".vm"));
		return Paths.get(stringWriter.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EResource#read()
	 */
	@Override
	@SneakyThrows
	public byte[] read() {
		if (this.relativePath.toString().endsWith(".vm")) {
			StringWriter stringWriter = new StringWriter();
			this.velocityEngine.evaluate(this.velocityContext, stringWriter, new String(), new String(Files.readAllBytes(this.rootPath.resolve(this.relativePath))));
			return stringWriter.toString().getBytes(StandardCharsets.UTF_8);
		}
		return Files.readAllBytes(this.rootPath.resolve(this.relativePath));
	}

}
