package br.xtool.core.service;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Collection;
import java.util.Map;

import br.xtool.core.representation.EProject;
import br.xtool.core.representation.ETemplate;

public interface FileService {

	Collection<ETemplate> getTemplates(Path rootPath, PathMatcher pathMatcher, Map<String, Object> vars);

	ETemplate getTemplate(Path rootPath, Path path, Map<String, Object> vars);

	String inlineTemplate(String inlineTemplate, Map<String, Object> vars);

	<T extends EProject> void copy(ETemplate template, T project);

	<T extends EProject> void copy(Collection<ETemplate> templates, T project);

}
