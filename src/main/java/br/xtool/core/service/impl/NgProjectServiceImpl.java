package br.xtool.core.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import br.xtool.core.representation.EJavaEnum;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.ENgClass;
import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDialog;
import br.xtool.core.representation.ENgEntity;
import br.xtool.core.representation.ENgEnum;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgPage;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.ENgService;
import br.xtool.core.representation.impl.ENgEntityImpl;
import br.xtool.core.representation.impl.ENgEnumImpl;
import br.xtool.core.representation.impl.ENgPageImpl;
import br.xtool.service.FileService;
import br.xtool.service.NgProjectService;
import br.xtool.service.ShellService;
import lombok.NonNull;
import strman.Strman;

@Service
public class NgProjectServiceImpl implements NgProjectService {

	@Autowired
	private ShellService shellService;

	// @Autowired
	// private WorkspaceService workspaceService;

	@Autowired
	private FileService fs;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.NgService#addDeclarationToModule(br.xtool.core.
	 * representation.ENgModule, br.xtool.core.representation.ENgComponent)
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
		this.shellService.runCmd(
				"node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --component-path=${componentPath} --component-name=${componentName}", vars);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.NgService#addProviderToModule(br.xtool.core.
	 * representation.ENgModule, br.xtool.core.representation.ENgService)
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
		this.shellService.runCmd("node ${xtoolNg} --module-path=${modulePath} --module-name=${moduleName} --service-path=${servicePath} --service-name=${serviceName}",
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.service.NgService#createNgPage(br.xtool.core.representation.
	 * ENgProject, br.xtool.core.representation.EProject.Version,
	 * br.xtool.core.representation.ENgModule, java.lang.String)
	 */
	@Override
	public ENgPage createNgPage(ENgProject ngProject, ENgModule ngModule, String name) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("pageFileName", ENgPage.genFileName(name));
				put("pageClassName", ENgPage.genClassName(name));
			}
		};
		this.fs.copy(Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("page"), vars, ngModule.getPath().getParent());
		Path ngPagePath = ngModule.getPath().getParent().resolve(ENgPage.genFileName(name)).resolve(ENgPage.genFileName(name).concat(".component.ts"));
		ENgPage ngPage = new ENgPageImpl(ngPagePath);
		this.addDeclarationToModule(ngProject, ngModule, ngPage);
		return ngPage;
	}

	@Override
	public ENgEntity createNgEntity(ENgProject ngProject, EJpaEntity entity) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("entityFileName", ENgClass.genFileName(entity.getName()));
				put("entityClassName", entity.getName());
				put("entity", entity);
				put("typescriptTypeMap", ENgClass.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("domain");
		Path destinationPath = ngProject.getNgAppModule().getPath().getParent().resolve("domain");

		this.fs.copy(resourcePath, vars, destinationPath);
		Path ngEntityPath = destinationPath.resolve(ENgClass.genFileName(entity.getName())).resolve(entity.getName().concat(".ts"));
		ENgEntity ngEntity = new ENgEntityImpl(ngEntityPath);
		return ngEntity;
	}

	@Override
	public ENgEnum createNgEnum(ENgProject ngProject, EJavaEnum javaEnum) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("javaEnumFileName", ENgClass.genFileName(javaEnum.getName()));
				put("javaEnumClassName", javaEnum.getName());
				put("javaEnum", javaEnum);
				put("javaEnumConstants", StringUtils.join(javaEnum.getConstants(), ","));
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("enums");
		Path destinationPath = ngProject.getNgAppModule().getPath().getParent().resolve("domain").resolve("enums");
		this.fs.copy(resourcePath, vars, destinationPath);
		Path ngEnumPath = destinationPath.resolve(ENgClass.genFileName(javaEnum.getName())).resolve(javaEnum.getName().concat(".ts"));
		
		ENgEnum ngEnum = new ENgEnumImpl(ngEnumPath);
		return ngEnum;
	}

	private void copyXtoolNg(ENgProject ngProject, Map<String, Object> vars) {
		if (Files.notExists(ngProject.getPath().resolve("scripts/xtool-ng.js"))) {
			this.fs.copy(Paths.get("angular/v5/archetype/scripts"), vars, ngProject.getPath());
		}
	}
}
