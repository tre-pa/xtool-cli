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
import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgModule;
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
}
