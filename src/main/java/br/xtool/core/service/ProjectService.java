package br.xtool.core.service;

import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import br.xtool.core.factory.EBootProjectV1Factory;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EProject;

@Service
public class ProjectService {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private ApplicationContext applicationContext;

	//	private Map<Class<? extends EProject>, Function<String, ? extends EProject>> projectFactories;

	private Table<Class<? extends EProject>, EProject.Version, Function<String, ? extends EProject>> projectFactories = HashBasedTable.create();

	@PostConstruct
	private void init() {
		this.projectFactories.put(EBootProject.class, EProject.Version.V1, this.applicationContext.getBean(EBootProjectV1Factory.class));
	}

	/**
	 * Carrega um projeto do workspace.
	 * 
	 * @param workspaceProjectClass
	 * @return
	 */
	public <T extends EProject> T load(Class<T> workspaceProjectClass) {
		return workspaceProjectClass.cast(this.workspaceService.getWorkingProject());
	}

	/**
	 * Cria um novo projeto no workspace.
	 * 
	 * @param projectClass
	 * @param name
	 * @return
	 */
	public <T extends EProject> T create(Class<T> projectClass, EProject.Version version, String name) {
		validateFactory(projectClass, version);
		return projectClass.cast(this.projectFactories.get(projectClass, version).apply(name));
	}

	private <T extends EProject> void validateFactory(Class<T> projectClass, EProject.Version version) {
		if (!this.projectFactories.contains(projectClass, version)) {
			throw new IllegalArgumentException(String.format("Factory para criação do projeto do tipo %s não encontrada.", projectClass.getSimpleName()));
		}
	}

}
