package br.xtool.core.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDialog;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgService;
import lombok.NonNull;

@Service
public class NgService {

	@Autowired
	private ShellService shellService;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private FileService fs;

	/**
	 * 
	 * @param ngModule
	 * @param ngComponent
	 */
	public <T extends ENgComponent> void addDeclarationToModule(@NonNull ENgModule ngModule, @NonNull T ngComponent) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("xtoolNg", FilenameUtils.concat(this.workspaceService.getWorkingProject().getDirectory().getPath(), "scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("componentPath", ngComponent.getFile().getAbsolutePath())
				.put("componentName", ngComponent.getName())
				.build();
		// @formatter:on
		if (Files.notExists(Paths.get((String) vars.get("xtoolNg")))) {
			this.fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --component-path=${componentPath} --component-name=${componentName}", vars);
	}

	/**
	 * 
	 * @param ngModule
	 * @param ngService
	 */
	public void addProviderToModule(@NonNull ENgModule ngModule, @NonNull ENgService ngService) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("xtoolNg", FilenameUtils.concat(this.workspaceService.getWorkingProject().getDirectory().getPath(), "scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("servicePath", ngService.getFile().getAbsolutePath())
				.put("serviceName", ngService.getName())
				.build();
		// @formatter:on
		if (Files.notExists(Paths.get((String) vars.get("xtoolNg")))) {
			this.fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --service-path=${servicePath} --service-name=${serviceName}", vars);
	}

	/**
	 * 
	 * @param ngModule
	 * @param ngDialog
	 */
	public void addEntryComponentsToModule(@NonNull ENgModule ngModule, @NonNull ENgDialog ngDialog) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("xtoolNg", FilenameUtils.concat(this.workspaceService.getWorkingProject().getDirectory().getPath(), "scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("dialogPath", ngDialog.getFile().getAbsolutePath())
				.put("dialogName", ngDialog.getName())
				.build();
		// @formatter:on
		if (Files.notExists(Paths.get((String) vars.get("xtoolNg")))) {
			this.fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --dialog-path=${dialogPath} --dialog-name=${dialogName}", vars);
	}
}
