package br.xtool.core.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EProject.Version;
import br.xtool.core.representation.EWorkspace;
import br.xtool.core.representation.impl.ENoneProjectImpl;
import br.xtool.core.representation.impl.EWorkspaceImpl;
import br.xtool.service.FileService;
import br.xtool.service.WorkspaceService;
import lombok.Getter;
import lombok.SneakyThrows;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

	@Value("${workspace}")
	private Path home;

	@Getter
	private EProject workingProject;

	@Autowired
	private FileService fs;

	@PostConstruct
	private void init() {
		this.workingProject = new ENoneProjectImpl(this.home);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.WorkspaceService#getWorkspace()
	 */
	@Override
	public EWorkspace getWorkspace() {
		return new EWorkspaceImpl(this.home);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.WorkspaceService#setWorkingProject(br.xtool.core.representation.EProject)
	 */
	@Override
	public void setWorkingProject(EProject project) {
		this.workingProject = project;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.WorkspaceService#getWorkingProject(java.lang.Class)
	 */
	@Override
	public <T extends EProject> T getWorkingProject(Class<T> projectClass) {
		return projectClass.cast(this.workingProject);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.WorkspaceService#createDirectory(java.lang.String)
	 */
	@Override
	@SneakyThrows
	public Path createDirectory(String name) {
		Path directory = this.home.resolve(name);
		if (Files.exists(directory)) {
			throw new IllegalArgumentException(String.format("O diretório %s já existe no workspace", name));
		}
		return Files.createDirectory(directory);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.WorkspaceService#createProject(java.lang.Class, java.lang.String, br.xtool.core.representation.EProject.Version)
	 */
	@Override
	public <T extends EProject> T createProject(Class<T> projectClass, EProject.Type type, String name, Version version, Map<String, Object> vars) {
		Path archetypePath = Paths.get(type.getName()).resolve(version.getName()).resolve("archetype");
		Path projectPath = this.createDirectory(name);
		this.fs.copy(archetypePath, vars, projectPath);
		return projectClass.cast(EProject.factory(projectClass, projectPath));
	}

}
