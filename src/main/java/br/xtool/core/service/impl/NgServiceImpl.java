package br.xtool.core.service.impl;

import java.nio.file.Files;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDialog;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgService;
import br.xtool.core.service.FileService;
import br.xtool.core.service.NgService;
import br.xtool.core.service.ShellService;
import br.xtool.core.service.WorkspaceService;
import lombok.NonNull;

@Service
public class NgServiceImpl implements NgService {

	@Autowired
	private ShellService shellService;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private FileService fs;

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.NgService#addDeclarationToModule(br.xtool.core.representation.ENgModule, br.xtool.core.representation.ENgComponent)
	 */
	@Override
	public <T extends ENgComponent> void addDeclarationToModule(@NonNull ENgModule ngModule, @NonNull T ngComponent) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("xtoolNg", this.workspaceService.getWorkingProject().getPath().resolve("scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("componentPath", ngComponent.getFile().getAbsolutePath())
				.put("componentName", ngComponent.getName())
				.build();
		// @formatter:on
		if (Files.notExists(this.workspaceService.getWorkingProject().getPath().resolve("scripts/xtool-ng.js"))) {
			throw new UnsupportedOperationException();
			//			this.fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --component-path=${componentPath} --component-name=${componentName}", vars);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.NgService#addProviderToModule(br.xtool.core.representation.ENgModule, br.xtool.core.representation.ENgService)
	 */
	@Override
	public void addProviderToModule(@NonNull ENgModule ngModule, @NonNull ENgService ngService) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("xtoolNg", this.workspaceService.getWorkingProject().getPath().resolve("scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("servicePath", ngService.getFile().getAbsolutePath())
				.put("serviceName", ngService.getName())
				.build();
		// @formatter:on
		if (Files.notExists(this.workspaceService.getWorkingProject().getPath().resolve("scripts/xtool-ng.js"))) {
			throw new UnsupportedOperationException();
			//			this.fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --service-path=${servicePath} --service-name=${serviceName}", vars);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.NgService#addEntryComponentsToModule(br.xtool.core.representation.ENgModule, br.xtool.core.representation.ENgDialog)
	 */
	@Override
	public void addEntryComponentsToModule(@NonNull ENgModule ngModule, @NonNull ENgDialog ngDialog) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("xtoolNg", this.workspaceService.getWorkingProject().getPath().resolve("scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("dialogPath", ngDialog.getFile().getAbsolutePath())
				.put("dialogName", ngDialog.getName())
				.build();
		// @formatter:on
		if (Files.notExists(this.workspaceService.getWorkingProject().getPath().resolve("scripts/xtool-ng.js"))) {
			throw new UnsupportedOperationException();
			//			this.fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --dialog-path=${dialogPath} --dialog-name=${dialogName}", vars);
	}
}
