package br.xtool.core.representation.impl;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import br.xtool.core.representation.ETemplate;

public class ETemplateImpl implements ETemplate {

	private Path path;

	private VelocityEngine velocityEngine;

	private VelocityContext velocityContext;

	private String content;

	public ETemplateImpl(Path path, VelocityEngine velocityEngine, VelocityContext velocityContext, String content) {
		super();
		this.path = path;
		this.velocityEngine = velocityEngine;
		this.velocityContext = velocityContext;
		this.content = content;
	}

	public ETemplateImpl(VelocityEngine velocityEngine, VelocityContext velocityContext, String content) {
		super();
		this.velocityEngine = velocityEngine;
		this.velocityContext = velocityContext;
		this.content = content;
	}

	@Override
	public String merge() {
		StringWriter stringWriter = new StringWriter();
		this.velocityEngine.evaluate(this.velocityContext, stringWriter, new String(), this.content);
		return stringWriter.toString();
	}

	@Override
	public Path getPath() {
		StringWriter stringWriter = new StringWriter();
		this.velocityEngine.evaluate(this.velocityContext, stringWriter, new String(), StringUtils.removeEnd(this.path.toString(), ".vm"));
		String mergedPath = stringWriter.toString();
		return Paths.get(mergedPath);
	}

}
