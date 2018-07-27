package br.xtool.core.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDialog;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgPage;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.ENgService;
import br.xtool.core.representation.EResource;
import br.xtool.core.service.FileService;
import br.xtool.core.service.NgService;
import br.xtool.core.service.ShellService;
import lombok.NonNull;

@Service
public class NgServiceImpl implements NgService {

	@Autowired
	private ShellService shellService;

	//	@Autowired
	//	private WorkspaceService workspaceService;

	@Autowired
	private FileService fs;

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.NgService#addDeclarationToModule(br.xtool.core.representation.ENgModule, br.xtool.core.representation.ENgComponent)
	 */
	@Override
	public <T extends ENgComponent> void addDeclarationToModule(ENgProject ngProject, @NonNull ENgModule ngModule, @NonNull T ngComponent) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("xtoolNg", ngProject.getPath().resolve("scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getPath().toString())
				.put("moduleName", ngModule.getName())
				.put("componentPath", ngComponent.getPath().toString())
				.put("componentName", ngComponent.getName())
				.build();
		// @formatter:on
		copyXtoolNg(ngProject, vars);
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --component-path=${componentPath} --component-name=${componentName}", vars);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.NgService#addProviderToModule(br.xtool.core.representation.ENgModule, br.xtool.core.representation.ENgService)
	 */
	@Override
	public void addProviderToModule(ENgProject ngProject, @NonNull ENgModule ngModule, @NonNull ENgService ngService) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("xtoolNg", ngProject.getPath().resolve("scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getPath().toString())
				.put("moduleName", ngModule.getName())
				.put("servicePath", ngService.getPath().toString())
				.put("serviceName", ngService.getName())
				.build();
		// @formatter:on
		copyXtoolNg(ngProject, vars);
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --service-path=${servicePath} --service-name=${serviceName}", vars);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.NgService#addEntryComponentsToModule(br.xtool.core.representation.ENgModule, br.xtool.core.representation.ENgDialog)
	 */
	@Override
	public void addEntryComponentsToModule(ENgProject ngProject, @NonNull ENgModule ngModule, @NonNull ENgDialog ngDialog) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("xtoolNg", ngProject.getPath().resolve("scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getPath().toString())
				.put("moduleName", ngModule.getName())
				.put("dialogPath", ngDialog.getPath().toString())
				.put("dialogName", ngDialog.getName())
				.build();
		// @formatter:on
		copyXtoolNg(ngProject, vars);
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --dialog-path=${dialogPath} --dialog-name=${dialogName}", vars);
	}

	@Override
	public ENgPage createNgPage(ENgProject ngProject) {
		return null;
	}

	private void copyXtoolNg(ENgProject ngProject, Map<String, Object> vars) {
		if (Files.notExists(ngProject.getPath().resolve("scripts/xtool-ng.js"))) {
			Collection<EResource> resources = this.fs.getTemplates(Paths.get("angular/v5/archetype/scripts"), vars);
			this.fs.copy(resources, ngProject.getPath());
		}
	}
}
