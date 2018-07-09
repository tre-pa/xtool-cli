package br.xtool.core.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.CommandLineExecutor;
import br.xtool.core.FS;
import br.xtool.core.WorkContext;
import br.xtool.core.representation.angular.ENgComponent;
import br.xtool.core.representation.angular.ENgDialog;
import br.xtool.core.representation.angular.ENgModule;
import br.xtool.core.representation.angular.ENgService;
import lombok.NonNull;

@Service
public class NgService {

	@Autowired
	private CommandLineExecutor cmdExecutor;

	@Autowired
	private WorkContext workContext;

	@Autowired
	private FS fs;

	/**
	 * 
	 * @param ngModule
	 * @param ngComponent
	 */
	public void addDeclarationToModule(@NonNull ENgModule ngModule, @NonNull ENgComponent ngComponent) {
		// @formatter:off
		Map<String, Object> vars = ImmutableMap.<String, Object>builder()
				.put("templatePath", "generators/angular/5.x/scaffold")
				.put("xtoolNg", FilenameUtils.concat(workContext.getDirectory().getPath(), "scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("componentPath", ngComponent.getFile().getAbsolutePath())
				.put("componentName", ngComponent.getName())
				.build();
		// @formatter:on
		if (Files.notExists(Paths.get((String) vars.get("xtoolNg")))) {
			fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		cmdExecutor.run("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --component-path=${componentPath} --component-name=${componentName}", vars);
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
				.put("xtoolNg", FilenameUtils.concat(workContext.getDirectory().getPath(), "scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("servicePath", ngService.getFile().getAbsolutePath())
				.put("serviceName", ngService.getName())
				.build();
		// @formatter:on
		if (Files.notExists(Paths.get((String) vars.get("xtoolNg")))) {
			fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		cmdExecutor.run("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --service-path=${servicePath} --service-name=${serviceName}", vars);
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
				.put("xtoolNg", FilenameUtils.concat(workContext.getDirectory().getPath(), "scripts/xtool-ng.js"))
				.put("modulePath", ngModule.getFile().getAbsolutePath())
				.put("moduleName", ngModule.getName())
				.put("dialogPath", ngDialog.getFile().getAbsolutePath())
				.put("dialogName", ngDialog.getName())
				.build();
		// @formatter:on
		if (Files.notExists(Paths.get((String) vars.get("xtoolNg")))) {
			fs.copy("${templatePath}/scripts/xtool-ng.js.vm", "scripts/xtool-ng.js", vars);
		}
		cmdExecutor.run("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --dialog-path=${dialogPath} --dialog-name=${dialogName}", vars);
	}
}
