package br.xtool.core.implementation.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.representation.JavaEnumRepresentation;
import br.xtool.core.FS;
import br.xtool.core.Shell;
import br.xtool.core.implementation.NgProject;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.NgClassRepresentation;
import br.xtool.core.representation.NgComponentRepresentation;
import br.xtool.core.representation.NgDialogRepresentation;
import br.xtool.core.representation.NgEntityRepresentation;
import br.xtool.core.representation.NgEnumRepresentation;
import br.xtool.core.representation.NgModuleRepresentation;
import br.xtool.core.representation.NgPageRepresentation;
import br.xtool.core.representation.NgProjectRepresentation;
import br.xtool.core.representation.NgServiceRepresentation;
import br.xtool.core.representation.impl.ENgEntityImpl;
import br.xtool.core.representation.impl.ENgEnumImpl;
import br.xtool.core.representation.impl.ENgPageImpl;
import lombok.NonNull;
import strman.Strman;

@Service
public class NgProjectImpl implements NgProject {

	@Autowired
	private Shell shell;

	// @Autowired
	// private WorkspaceService workspaceService;

	@Autowired
	private FS fs;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.NgService#addDeclarationToModule(br.xtool.core.
	 * representation.ENgModule, br.xtool.core.representation.ENgComponent)
	 */
	@Override
	public <T extends NgComponentRepresentation> void addDeclarationToModule(NgProjectRepresentation ngProject, @NonNull NgModuleRepresentation ngModule, @NonNull T ngComponent) {
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
		this.shell.runCmd(
				"node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --component-path=${componentPath} --component-name=${componentName}", vars);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.NgService#addProviderToModule(br.xtool.core.
	 * representation.ENgModule, br.xtool.core.representation.ENgService)
	 */
	@Override
	public void addProviderToModule(NgProjectRepresentation ngProject, @NonNull NgModuleRepresentation ngModule, @NonNull NgServiceRepresentation ngService) {
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
		this.shell.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --service-path=${servicePath} --service-name=${serviceName}",
				vars);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.service.NgService#addEntryComponentsToModule(br.xtool.core.
	 * representation.ENgModule, br.xtool.core.representation.ENgDialog)
	 */
	@Override
	public void addEntryComponentsToModule(NgProjectRepresentation ngProject, @NonNull NgModuleRepresentation ngModule, @NonNull NgDialogRepresentation ngDialog) {
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
		this.shell.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --dialog-path=${dialogPath} --dialog-name=${dialogName}", vars);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.service.NgService#createNgPage(br.xtool.core.representation.
	 * ENgProject, br.xtool.core.representation.EProject.Version,
	 * br.xtool.core.representation.ENgModule, java.lang.String)
	 */
	@Override
	public NgPageRepresentation createNgPage(NgProjectRepresentation ngProject, NgModuleRepresentation ngModule, String name) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("pageFileName", NgPageRepresentation.genFileName(name));
				put("pageClassName", NgPageRepresentation.genClassName(name));
			}
		};
		this.fs.copy(Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("page"), vars, ngModule.getPath().getParent());
		Path ngPagePath = ngModule.getPath().getParent().resolve(NgPageRepresentation.genFileName(name)).resolve(NgPageRepresentation.genFileName(name).concat(".component.ts"));
		NgPageRepresentation ngPage = new ENgPageImpl(ngPagePath);
		this.addDeclarationToModule(ngProject, ngModule, ngPage);
		return ngPage;
	}

	@Override
	public NgEntityRepresentation createNgEntity(NgProjectRepresentation ngProject, EntityRepresentation entity) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("entityFileName", NgClassRepresentation.genFileName(entity.getName()));
				put("entityClassName", entity.getName());
				put("entity", entity);
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("domain");
		Path destinationPath = ngProject.getNgAppModule().getPath().getParent().resolve("domain");

		this.fs.copy(resourcePath, vars, destinationPath);
		Path ngEntityPath = destinationPath.resolve(NgClassRepresentation.genFileName(entity.getName())).resolve(entity.getName().concat(".ts"));
		NgEntityRepresentation ngEntity = new ENgEntityImpl(ngEntityPath);
		return ngEntity;
	}

	@Override
	public NgEnumRepresentation createNgEnum(NgProjectRepresentation ngProject, JavaEnumRepresentation javaEnum) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("javaEnumFileName", NgClassRepresentation.genFileName(javaEnum.getName()));
				put("javaEnumClassName", javaEnum.getName());
				put("javaEnum", javaEnum);
				put("javaEnumConstants", StringUtils.join(javaEnum.getConstants(), ","));
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("enums");
		Path destinationPath = ngProject.getNgAppModule().getPath().getParent().resolve("domain").resolve("enums");
		this.fs.copy(resourcePath, vars, destinationPath);
		Path ngEnumPath = destinationPath.resolve(NgClassRepresentation.genFileName(javaEnum.getName())).resolve(javaEnum.getName().concat(".ts"));
		
		NgEnumRepresentation ngEnum = new ENgEnumImpl(ngEnumPath);
		return ngEnum;
	}

	private void copyXtoolNg(NgProjectRepresentation ngProject, Map<String, Object> vars) {
		if (Files.notExists(ngProject.getPath().resolve("scripts/xtool-ng.js"))) {
			this.fs.copy(Paths.get("angular/v5/archetype/scripts"), vars, ngProject.getPath());
		}
	}
}
