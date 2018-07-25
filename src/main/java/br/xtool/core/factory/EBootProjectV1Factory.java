package br.xtool.core.factory;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.ETemplate;
import br.xtool.core.representation.impl.EBootProjectImpl;
import br.xtool.core.service.FileService;
import br.xtool.core.service.WorkspaceService;
import lombok.SneakyThrows;

@Component
public class EBootProjectV1Factory implements Function<String, EBootProject> {

	@Autowired
	private FileService fs;

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	@SneakyThrows
	public EBootProject apply(String name) {

		Path projectPath = this.workspaceService.getHome().resolve(name);
		if (Files.exists(projectPath)) throw new IllegalArgumentException(String.format("O projeto com nome %s já existe no workspace.", name));
		Files.createDirectory(projectPath);
		EBootProject project = new EBootProjectImpl(projectPath);

		Map<String, Object> vars = new HashMap<>();
		vars.put("projectName", EBootProject.genProjectName(name));
		vars.put("rootPackage", EBootProject.genRootPackage(name));
		vars.put("baseClassName", EBootProject.genBaseClassName(name));

		ConsoleLog.print(ConsoleLog.cyan("\t-- Projeto Base --"));

		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.vm");
		Collection<ETemplate> templates = this.fs.getTemplates(Paths.get("src/main/resources/templates/springboot/1.5.x/archetype"), pathMatcher, vars);
		this.fs.copy(templates, project);

		return project;
	}

}
