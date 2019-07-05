package br.xtool.core.implementation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

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
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
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
		workingProject = new NoneProjectRepresentationImpl(home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#getWorkspace()
	 */
	@Override
	public WorkspaceRepresentation getWorkspace() {
		return new WorkspaceRepresentationImpl(home);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#setWorkingProject(br.xtool.core. representation.EProject)
	 */
	@Override
	public void setWorkingProject(ProjectRepresentation project) {
		workingProject = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#getWorkingProject(java.lang.Class)
	 */
	@Override
	public <T extends ProjectRepresentation> T getWorkingProject(Class<T> projectClass) {
		return projectClass.cast(workingProject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.WorkspaceService#createDirectory(java.lang.String)
	 */
	@Override
	@SneakyThrows
	public Path createDirectory(String name) {
		Path directory = home.resolve(name);
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
		Path projectPath = createDirectory(name);
		fs.copy(archetypePath, vars, projectPath);
		return (T) type.getProjectClass().cast(ProjectRepresentation.factory((Class<T>) type.getProjectClass(), projectPath));
	}

	@Override
	public Type getWorkingProjectType() {
		return this.getWorkingProject().getProjectType();
	}

	@Override
	public boolean isSpringBootProject() {
		return getWorkingProjectType().equals(ProjectRepresentation.Type.SPRINGBOOT);
	}

	@Override
	public boolean isAngularProject() {
		return getWorkingProjectType().equals(ProjectRepresentation.Type.ANGULAR);
	}

	@Override
	public boolean isSpringBootNgProject() {
		return getWorkingProjectType().equals(ProjectRepresentation.Type.SPRINGBOOTNG);
	}

	@Override
	public Optional<SpringBootProjectRepresentation> getSpringBootProject() {
		if (isSpringBootProject()) {
			return Optional.of(this.getWorkingProject(SpringBootProjectRepresentation.class));
		} else if (isSpringBootNgProject()) {
			return Optional.of(this.getWorkingProject(SpringBootNgProjectRepresentation.class).getSpringBootProject());
		}
		return Optional.empty();
	}

	@Override
	public Optional<NgProjectRepresentation> getAngularProject() {
		if (isAngularProject()) {
			return Optional.of(this.getWorkingProject(NgProjectRepresentation.class));
		} else if (isSpringBootNgProject()) {
			return Optional.of(this.getWorkingProject(SpringBootNgProjectRepresentation.class).getAngularProject());
		}
		return Optional.empty();
	}

}
