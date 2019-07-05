package br.xtool.core.implementation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.xtool.core.FS;
import br.xtool.core.Workspace;
import br.xtool.core.implementation.representation.NoneProjectRepresentationImpl;
import br.xtool.core.implementation.representation.WorkspaceRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.ProjectRepresentation.Type;
import br.xtool.core.representation.WorkspaceRepresentation;
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
		this.workingProject = new NoneProjectRepresentationImpl(this.home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#getWorkspace()
	 */
	@Override
	public WorkspaceRepresentation getWorkspace() {
		return new WorkspaceRepresentationImpl(this.home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#setWorkingProject(br.xtool.core. representation.EProject)
	 */
	@Override
	public void setWorkingProject(ProjectRepresentation project) {
		this.workingProject = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#getWorkingProject(java.lang.Class)
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
	 * @see br.xtool.core.Workspace#createProject(br.xtool.core.representation. ProjectRepresentation.Type, java.lang.String, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ProjectRepresentation> T createProject(Type type, String version, String name, Map<String, Object> vars) {
		Path archetypePath = Paths.get(type.getName()).resolve(version).resolve("archetype");
		Path projectPath = this.createDirectory(name);
		this.fs.copy(archetypePath, vars, projectPath);
		return (T) type.getProjectClass().cast(ProjectRepresentation.factory((Class<T>) type.getProjectClass(), projectPath));
	}

	@Override
	public Type getWorkingProjectType() {
		return this.getWorkingProject().getProjectType();
	}

}
