package br.xtool.core.service.impl;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.EProject;
import br.xtool.core.representation.ETemplate;
import br.xtool.core.representation.impl.ETemplateImpl;
import br.xtool.core.service.FileService;
import lombok.SneakyThrows;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	@SneakyThrows
	public Collection<ETemplate> getTemplates(Path rootPath, PathMatcher pathMatcher, Map<String, Object> vars) {
		VelocityContext velocityContext = new VelocityContext(vars);
		// @formatter:off
		return Files.walk(rootPath)
			.filter(pathMatcher::matches)
			.map(path -> new ETemplateImpl(rootPath.relativize(path),this.velocityEngine, velocityContext, this.readFileAsString(path)))
			.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public ETemplate getTemplate(Path rootPath, Path path, Map<String, Object> vars) {
		VelocityContext velocityContext = new VelocityContext(vars);
		return new ETemplateImpl(rootPath.relativize(path), this.velocityEngine, velocityContext, this.readFileAsString(path));
	}

	@Override
	public String inlineTemplate(String inlineTemplate, Map<String, Object> vars) {
		VelocityContext velocityContext = new VelocityContext(vars);
		return new ETemplateImpl(this.velocityEngine, velocityContext, inlineTemplate).merge();
	}

	@SneakyThrows
	private String readFileAsString(Path path) {
		return new String(Files.readAllBytes(path));
	}

	@Override
	@SneakyThrows
	public <T extends EProject> void copy(ETemplate template, T project) {
		Path finalPath = project.getDirectory().getPath().resolve(template.getPath());
		if (Files.notExists(finalPath.getParent())) Files.createDirectories(finalPath.getParent());
		BufferedWriter bufferedWriter = Files.newBufferedWriter(finalPath, StandardCharsets.UTF_8);
		bufferedWriter.write(template.merge());
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	@Override
	public <T extends EProject> void copy(Collection<ETemplate> templates, T project) {
		templates.forEach(template -> this.copy(template, project));
	}

}
