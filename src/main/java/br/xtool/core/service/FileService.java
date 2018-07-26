package br.xtool.core.service;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Collection;
import java.util.Map;

import br.xtool.core.representation.EResource;

public interface FileService {

	Collection<EResource> getTemplates(Path rootPath, PathMatcher pathMatcher, Map<String, Object> vars);

	void copy(Collection<EResource> resources, Path path);

}
