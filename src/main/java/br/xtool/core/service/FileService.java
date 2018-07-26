package br.xtool.core.service;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Collection;
import java.util.Map;

import br.xtool.core.template.Resource;

public interface FileService {

	Collection<Resource> getTemplates(Path rootPath, PathMatcher pathMatcher, Map<String, Object> vars);

	void copy(Collection<Resource> resources, Path path);

}
