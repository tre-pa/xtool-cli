package br.xtool.core.implementation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.xtool.core.FS;
import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.ProjectRepresentation.Type;
import br.xtool.core.representation.ProjectRepresentation.Version;
import br.xtool.core.representation.WorkspaceRepresentation;
import br.xtool.core.representation.impl.ENoneProjectImpl;
import br.xtool.core.representation.impl.EWorkspaceImpl;
import lombok.Getter;
import lombok.SneakyThrows;

@Service
public class WorkspaceImpl implements Workspace {

	@Value("${workspace}")
	private Path home;

	@Getter
	private ProjectRepresentation workingProject;

	@Autowired
	private FS fs;

	@PostConstruct
	private void init() {
		this.workingProject = new ENoneProjectImpl(this.home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#getWorkspace()
	 */
	@Override
	public WorkspaceRepresentation getWorkspace() {
		return new EWorkspaceImpl(this.home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#setWorkingProject(br.xtool.core.
	 * representation.EProject)
	 */
	@Override
	public void setWorkingProject(ProjectRepresentation project) {
		this.workingProject = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.service.WorkspaceService#getWorkingProject(java.lang.Class)
	 */
	@Override
	public <T extends ProjectRepresentation> T getWorkingProject(Class<T> projectClass) {
		return projectClass.cast(this.workingProject);
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @see br.xtool.core.service.WorkspaceService#createProject(java.lang.Class,
	 * java.lang.String, br.xtool.core.representation.EProject.Version)
	 */
	@Override
	public <T extends ProjectRepresentation> T createProject(Class<T> projectClass, ProjectRepresentation.Type type, String name, Version version, Map<String, Object> vars) {
		Path archetypePath = Paths.get(type.getName()).resolve(version.getName()).resolve("archetype");
		Path projectPath = this.createDirectory(name);
		this.fs.copy(archetypePath, vars, projectPath);
		return projectClass.cast(ProjectRepresentation.factory(projectClass, projectPath));
	}

	@Override
	public <T extends ProjectRepresentation> T createProject(Class<T> projectClass, Type type, String name, Version version, String qualifier, Map<String, Object> vars) {
		if (StringUtils.isEmpty(qualifier))
			return this.createProject(projectClass, type, name, version, vars);
		Path archetypePath = Paths.get(type.getName()).resolve(version.getName().concat("-").concat(qualifier)).resolve("archetype");
		Path projectPath = this.createDirectory(name);
		this.fs.copy(archetypePath, vars, projectPath);
		return projectClass.cast(ProjectRepresentation.factory(projectClass, projectPath));
	}

}
