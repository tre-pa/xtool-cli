package br.xtool.core.implementation.service;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.FS;
import br.xtool.core.Shell;
import br.xtool.core.TemplateBuilder;
import br.xtool.core.Workspace;
import br.xtool.core.helper.InflectorHelper;
import br.xtool.core.helper.JsonHelper;
import br.xtool.core.helper.StringHelper;
import br.xtool.core.implementation.representation.NgDetailRepresentationImpl;
import br.xtool.core.implementation.representation.NgEditRepresentationImpl;
import br.xtool.core.implementation.representation.NgEntityRepresentationImpl;
import br.xtool.core.implementation.representation.NgEnumRepresentationImpl;
import br.xtool.core.implementation.representation.NgListRepresentationImpl;
import br.xtool.core.implementation.representation.NgServiceRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgDetailRepresentation;
import br.xtool.core.representation.angular.NgEditRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgListRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgPageRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgRoute;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.template.NgDetailTemplates;
import br.xtool.core.template.NgEditTemplates;
import br.xtool.core.template.NgListTemplates;
import br.xtool.service.AngularService;
import lombok.SneakyThrows;
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

	@Autowired
	private ApplicationContext appCtx;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#newApp(java.lang.String, java.lang.String)
	 */
	@Override
	public void newApp(String name, String description, String version) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("projectName", name);
				put("projectDesc", description);
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
		this.shellService.runCmd(project.getPath(), "chmod +x scripts/keycloak/register-client.sh");
		this.shellService.runCmd(project.getPath(), "git init > /dev/null 2>&1");
		this.shellService.runCmd(project.getPath(), "git add . > /dev/null 2>&1");
		this.shellService.runCmd(project.getPath(), "git commit -m \"Inicial commit\" > /dev/null 2>&1");
		ConsoleLog.print(ConsoleLog.cyan("\t-- Commit inicial realizado no git. --"));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#createNgEntity(br.xtool.core.representation. springboot.EntityRepresentation)
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

		entity.getAttributes().stream().filter(EntityAttributeRepresentation::isEnumField).map(EntityAttributeRepresentation::getEnum).map(Optional::get)
				.forEach(this::genNgEnum);
		return ngEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#createNgEnum(br.xtool.core.representation. springboot.JavaEnumRepresentation)
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
	 * @see br.xtool.service.AngularService#genNgService(br.xtool.core.representation. springboot.EntityRepresentation)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#genNgList(br.xtool.core.representation. springboot.EntityRepresentation,
	 * br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	@Override
	public NgListRepresentation genNgList(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("ngListTemplates", appCtx.getBean(NgListTemplates.class));
				put("entity", entity);
				put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("list");
		Path destinationPath = ngModule.getPath().getParent().resolve(entity.getTsFileName());
		Path componentPath = destinationPath.resolve(String.format("%s-list", entity.getTsFileName()));

		this.fs.copy(resourcePath, vars, destinationPath);
		NgListRepresentation ngList = new NgListRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		addComponent(ngModule, ngList);
		addRoute(ngModule, NgRoute.of(ngList));
		addImport(ngModule, ngList.getName());

		return ngList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#genNgDetail(br.xtool.core.representation. springboot.EntityRepresentation,
	 * br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	@Override
	public NgDetailRepresentation genNgDetail(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("ngDetailTemplates", appCtx.getBean(NgDetailTemplates.class));
				put("entity", entity);
				put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("detail");
		Path destinationPath = ngModule.getPath().getParent().resolve(entity.getTsFileName());
		Path componentPath = destinationPath.resolve(String.format("%s-detail", entity.getTsFileName()));

		this.fs.copy(resourcePath, vars, destinationPath);
		NgDetailRepresentation ngDetail = new NgDetailRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		addComponent(ngModule, ngDetail);
		addRoute(ngModule, NgRoute.of(ngDetail));
		addImport(ngModule, ngDetail.getName());

		return ngDetail;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#genNgEdit(br.xtool.core.representation. springboot.EntityRepresentation,
	 * br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	@Override
	public NgEditRepresentation genNgEdit(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = genNgAssociatedProject();

		String entityFileName = NgClassRepresentation.genFileName(entity.getName()).concat("-edit");
		String entityFolderName = Strman.toKebabCase(entity.getInstanceName());

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("ngEditTemplates", appCtx.getBean(NgEditTemplates.class));
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
		Path componentPath = destinationPath.resolve(String.format("%s-edit", entity.getTsFileName()));

		this.fs.copy(resourcePath, vars, destinationPath);
		NgEditRepresentation ngEdit = new NgEditRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		addComponent(ngModule, ngEdit);
		addRoute(ngModule, NgRoute.of(ngEdit));
		addImport(ngModule, ngEdit.getName());

		return ngEdit;
	}

	@Override
	public void printNgLists(NgProjectRepresentation project) {
		ConsoleLog.print(ConsoleLog.cyan(String.format("-- Componentes List(%d) --", project.getNgLists().size())));
		// @formatter:off
		project.getNgLists()
			.stream()
			.forEach(entity -> ConsoleLog.print(entity.getName()));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.service.AngularService#addRoute(br.xtool.core.representation.angular.NgModuleRepresentation, br.xtool.core.representation.angular.NgRoute)
	 */
	public void addRoute(NgModuleRepresentation module, NgRoute route) {
		List<NgRoute> routes = module.getRoutes();
		if (routes.get(0).getChildren().stream().noneMatch(pNgRoute -> pNgRoute.equals(route))) {
			routes.get(0).getChildren().add(route);

			String content = module.getTsFileContent();
			Pattern pattern = Pattern.compile(NgModuleRepresentation.ROUTE_PATTERN);
			Pair<Integer, Integer> idxRoute = StringHelper.indexOfPattern(pattern, content);
			String startRouteArray = content.substring(idxRoute.getRight());
			Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startRouteArray);

			String start = content.substring(0, idxRoute.getRight());
			String end = content.substring(idxRoute.getRight() + idxOfFirstArray.getRight(), content.length() - 1);

			String newContent = start.concat(JsonHelper.serialize(routes).replaceAll("\"", "'")).concat(end);
			save(module, newContent);
			return;
		}
		ConsoleLog.print(ConsoleLog.yellow(String.format("Componente '%s' já registrado em uma rota.", route.getComponent())));
	}

	private void addComponent(NgModuleRepresentation module, NgComponentRepresentation component) {
		List<String> declarations = new ArrayList<>(module.getModuleDeclarations());
		if (!declarations.contains(component.getName())) {
			declarations.add(component.getName());
			String content = module.getTsFileContent();

			Pattern pattern = Pattern.compile(NgModuleRepresentation.DECLARATION_PATTERN);
			int idx = StringHelper.indexOfPattern(pattern, content).getRight();
			String startDeclarationArray = content.substring(idx);
			Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startDeclarationArray);
			Pair<Integer, Integer> idxDeclarations = Pair.of(idx + 1, idx + idxOfFirstArray.getRight() - 1);

			String start = content.substring(0, idxDeclarations.getLeft());
			String end = content.substring(idxDeclarations.getRight(), content.length() - 1);
			String newContent = start.concat("\n    ").concat(StringUtils.join(declarations, ",\n    ")).concat("\n  ").concat(end);
			save(module, newContent);
			return;
		}
		ConsoleLog.print(ConsoleLog.yellow(String.format("Componente '%s' já registrado no módulo.", component.getName())));
	}

	@SneakyThrows
	private void addImport(NgModuleRepresentation module, String componentName) {
		// @formatter:off
		NgComponentRepresentation component = module.getProject().getNgComponents().stream()
				.filter(ngC -> ngC.getName().equals(componentName))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format("NgComponent %s não encontrado.", componentName)));
		
		if (module.getImports()
				.stream()
				.flatMap(ngImport -> ngImport.getItems().stream())
				.noneMatch(ngImportName -> ngImportName.equals(component.getName()))) {
			// @formatter:on
			List<String> lines = Files.readAllLines(module.getPath(), StandardCharsets.UTF_8);
			Path importPath = module.getPath().getParent().relativize(component.getNgTsClass().getPath());
			// @formatter:off
			String importContent = TemplateBuilder.builder()
					.tpl("import { {{componentName}} } from './{{pathName}}';")
					.put("componentName", component.getName())
					.put("pathName", FilenameUtils.removeExtension(importPath.toString()))
					.build();
			// @formatter:on
			lines.add(module.getImports().size(), importContent.trim());
			save(module, StringUtils.join(lines, "\n"));
		}
	}

	@Override
	public void addNavigation(NgPageRepresentation page, NgListRepresentation list) {

	}

	private NgProjectRepresentation genNgAssociatedProject() {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		NgProjectRepresentation ngProject = springBootProject.getAssociatedAngularProject()
				.orElseThrow(() -> new IllegalArgumentException("Não há nenhum projeto Angular associado ao projeto: " + springBootProject.getName()));
		return ngProject;
	}

	@SneakyThrows
	private void save(NgModuleRepresentation module, String newContent) {
		try (BufferedWriter writer = Files.newBufferedWriter(module.getPath())) {
			writer.write(newContent);
			writer.flush();
		}
	}

}
