package br.xtool.core.implementation.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.FS;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.helper.InflectorHelper;
import br.xtool.core.implementation.representation.NgEntityRepresentationImpl;
import br.xtool.core.implementation.representation.NgEnumRepresentationImpl;
import br.xtool.core.implementation.representation.NgServiceRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.service.AngularService;
import strman.Strman;

@Service
@Lazy
public class AngularServiceImpl implements AngularService {

	@Autowired
	private Shell shellService;

	@Autowired
	private Workspace workspace;

	@Autowired
	private FS fs;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#newApp(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void newApp(String name, String version) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("projectName", name);
			}
		};
		// @formatter:off
		NgProjectRepresentation project = this.workspace.createProject(
				ProjectRepresentation.Type.ANGULAR, 
				version,
				name, 
				vars);
		// @formatter:on
		ConsoleLog.print(ConsoleLog.cyan("\t-- npm install --"));
		this.shellService.runCmd(project.getPath(), "npm i && code .", vars);
		this.shellService.runCmd(project.getPath(), "git init");
		this.shellService.runCmd(project.getPath(), "git add .");
		this.shellService.runCmd(project.getPath(), "git commit -m \"Inicial commit\" ");

		this.workspace.setWorkingProject(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.service.AngularService#createNgEntity(br.xtool.core.representation.
	 * springboot.EntityRepresentation)
	 */
	@Override
	public NgEntityRepresentation genNgEntity(EntityRepresentation entity) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();
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
		NgEntityRepresentation ngEntity = new NgEntityRepresentationImpl(ngEntityPath);

		entity.getAttributes().stream().filter(EntityAttributeRepresentation::isEnumField).map(EntityAttributeRepresentation::getEnum).map(Optional::get).forEach(this::genNgEnum);
		return ngEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.service.AngularService#createNgEnum(br.xtool.core.representation.
	 * springboot.JavaEnumRepresentation)
	 */
	@Override
	public NgEnumRepresentation genNgEnum(JavaEnumRepresentation javaEnum) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();
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

		NgEnumRepresentation ngEnum = new NgEnumRepresentationImpl(ngEnumPath);
		return ngEnum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.service.AngularService#genNgService(br.xtool.core.representation.
	 * springboot.EntityRepresentation)
	 */
	@Override
	public NgServiceRepresentation genNgService(EntityRepresentation entity) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("entityFileName", NgClassRepresentation.genFileName(entity.getName()));
				put("entityClassName", entity.getName());
				put("entity", entity);
				put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("service");
		Path destinationPath = ngProject.getNgAppModule().getPath().getParent().resolve("service");

		this.fs.copy(resourcePath, vars, destinationPath);
		Path ngServicePath = destinationPath.resolve(NgClassRepresentation.genFileName(entity.getName()).concat(".service")).resolve(entity.getName().concat(".ts"));
		NgServiceRepresentation ngService = new NgServiceRepresentationImpl(ngServicePath);
		return ngService;
	}

	@Override
	public void genNgList(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();

		String entityFileName = NgClassRepresentation.genFileName(entity.getName()).concat("-list");
		String entityFolderName = Strman.toKebabCase(entity.getInstanceName());

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("entityFileName", entityFileName);
				put("entityFolderName", entityFolderName);
				put("entityClassName", entity.getName());
				put("entity", entity);
				put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("list");
		Path destinationPath = ngModule.getPath().getParent().resolve(entityFolderName);

		this.fs.copy(resourcePath, vars, destinationPath);
	}

	@Override
	public void genNgDetail(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();

//		String entityFileName = NgClassRepresentation.genFileName(entity.getName()).concat("-detail");
//		String entityFolderName = Strman.toKebabCase(entity.getInstanceName());

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("entity", entity);
				put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("detail");
		Path destinationPath = ngModule.getPath().getParent().resolve(entity.getTsFileName());

		this.fs.copy(resourcePath, vars, destinationPath);

	}

	@Override
	public void genNgEdit(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();

		String entityFileName = NgClassRepresentation.genFileName(entity.getName()).concat("-edit");
		String entityFolderName = Strman.toKebabCase(entity.getInstanceName());

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("entityFileName", entityFileName);
				put("entityTsFileName", Strman.toKebabCase(entity.getInstanceName()));
				put("entityFolderName", entityFolderName);
				put("entityClassName", entity.getName());
				put("entity", entity);
				put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("edit");
		Path destinationPath = ngModule.getPath().getParent().resolve(entityFolderName);

		this.fs.copy(resourcePath, vars, destinationPath);

	}

	private NgProjectRepresentation genNgAssociatedProject() {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		NgProjectRepresentation ngProject = springBootProject.getAssociatedAngularProject()
				.orElseThrow(() -> new IllegalArgumentException("Não há nenhum projeto Angular associado ao projeto: " + springBootProject.getName()));
		return ngProject;
	}

}
