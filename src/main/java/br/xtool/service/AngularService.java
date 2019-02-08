package br.xtool.service;

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
import br.xtool.core.implementation.representation.NgEntityRepresentationImpl;
import br.xtool.core.implementation.representation.NgEnumRepresentationImpl;
import br.xtool.core.implementation.representation.NgServiceRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.util.Inflector;
import strman.Strman;

@Service
@Lazy
public class AngularService {

	@Autowired
	private Shell shellService;

	@Autowired
	private Workspace workspace;

	@Autowired
	private FS fs;

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

	/**
	 * Cria uma nova classe Typescript de dominio em src/app/domain
	 * 
	 * @param ngProject Projeto Angular
	 * @param entity    classe Jpa
	 * @return classe Typescript
	 */
	public NgEntityRepresentation createNgEntity(EntityRepresentation entity) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		NgProjectRepresentation ngProject = springBootProject.getAssociatedAngularProject()
				.orElseThrow(() -> new IllegalArgumentException("Não há nenhum projeto Angular associado ao projeto: " + springBootProject.getName()));
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

		entity.getAttributes().stream().filter(EntityAttributeRepresentation::isEnumField).map(EntityAttributeRepresentation::getEnum).map(Optional::get).forEach(this::createNgEnum);
		return ngEntity;
	}

	/**
	 * 
	 * @param ngProject
	 * @param javaEnum
	 * @return
	 */
	public NgEnumRepresentation createNgEnum(JavaEnumRepresentation javaEnum) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		NgProjectRepresentation ngProject = springBootProject.getAssociatedAngularProject()
				.orElseThrow(() -> new IllegalArgumentException("Não há nenhum projeto Angular associado ao projeto: " + springBootProject.getName()));
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

	/**
	 * Cria uma nova classe Typescript de dominio em src/app/domain
	 * 
	 * @param ngProject Projeto Angular
	 * @param entity    classe Jpa
	 * @return classe Typescript
	 */
	public NgServiceRepresentation genNgService(EntityRepresentation entity) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		NgProjectRepresentation ngProject = springBootProject.getAssociatedAngularProject()
				.orElseThrow(() -> new IllegalArgumentException("Não há nenhum projeto Angular associado ao projeto: " + springBootProject.getName()));
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("entityFileName", NgClassRepresentation.genFileName(entity.getName()));
				put("entityClassName", entity.getName());
				put("entity", entity);
				put("entityApiName", Inflector.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
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
}
