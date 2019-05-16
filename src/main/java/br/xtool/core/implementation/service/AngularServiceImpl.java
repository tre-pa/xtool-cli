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
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import br.xtool.core.ConsoleLog;
import br.xtool.core.FS;
import br.xtool.core.Shell;
import br.xtool.core.TemplateBuilder;
import br.xtool.core.Workspace;
import br.xtool.core.helper.InflectorHelper;
import br.xtool.core.helper.JsonHelper;
import br.xtool.core.helper.StringHelper;
import br.xtool.core.implementation.representation.NgCrudRepresentationImpl;
import br.xtool.core.implementation.representation.NgDetailRepresentationImpl;
import br.xtool.core.implementation.representation.NgEditRepresentationImpl;
import br.xtool.core.implementation.representation.NgEntityRepresentationImpl;
import br.xtool.core.implementation.representation.NgEnumRepresentationImpl;
import br.xtool.core.implementation.representation.NgListRepresentationImpl;
import br.xtool.core.implementation.representation.NgServiceRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgCrudRepresentation;
import br.xtool.core.representation.angular.NgDetailRepresentation;
import br.xtool.core.representation.angular.NgEditRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgEnumRepresentation;
import br.xtool.core.representation.angular.NgListRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgPageNavigationRepresentation;
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
				this.put("projectName", name);
				this.put("projectDesc", description);
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
		NgProjectRepresentation ngProject = this.genNgAssociatedProject();
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("Strman", Strman.class);
				this.put("entityFileName", NgClassRepresentation.genFileName(entity.getName()));
				this.put("entityClassName", entity.getName());
				this.put("entity", entity);
				this.put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
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
	 * @see br.xtool.service.AngularService#createNgEnum(br.xtool.core.representation. springboot.JavaEnumRepresentation)
	 */
	@Override
	public NgEnumRepresentation genNgEnum(JavaEnumRepresentation javaEnum) {
		NgProjectRepresentation ngProject = this.genNgAssociatedProject();
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("Strman", Strman.class);
				this.put("javaEnumFileName", NgClassRepresentation.genFileName(javaEnum.getName()));
				this.put("javaEnumClassName", javaEnum.getName());
				this.put("javaEnum", javaEnum);
				this.put("javaEnumConstants", StringUtils.join(javaEnum.getConstants(), ","));
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
		entity.getAssociatedNgEntity().orElseGet(() -> this.genNgEntity(entity));

		NgProjectRepresentation ngProject = this.genNgAssociatedProject();
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("Strman", Strman.class);
				this.put("entityFileName", NgClassRepresentation.genFileName(entity.getName()));
				this.put("entityClassName", entity.getName());
				this.put("entity", entity);
				this.put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				this.put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
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
	public NgCrudRepresentation genNgCrud(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		entity.getAssociatedNgService().orElseGet(() -> this.genNgService(entity));

		NgListRepresentation ngList = this.genNgList(entity, ngModule);
		NgDetailRepresentation ngDetail = this.genNgDetail(entity, ngModule);
		NgEditRepresentation ngEdit = entity.isImmutable() ? null : this.genNgEdit(entity, ngModule);
		if (Objects.nonNull(ngEdit)) {
			NgCrudRepresentation ngCrud = new NgCrudRepresentationImpl(ngModule, ngList, ngDetail, ngEdit);
			this.addComponent(ngModule, ngCrud);
			this.addToRoute(ngModule, ngCrud);
			ngModule.getAssociatedPage().ifPresent(ngPage -> this.addNavigation(ngPage, ngList));
			return ngCrud;
		}
		NgCrudRepresentation ngCrud = new NgCrudRepresentationImpl(ngModule, ngList, ngDetail);
		this.addComponent(ngModule, ngCrud);
		this.addToRoute(ngModule, ngCrud);
		ngModule.getAssociatedPage().ifPresent(ngPage -> this.addNavigation(ngPage, ngList));
		return ngCrud;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#genNgList(br.xtool.core.representation. springboot.EntityRepresentation,
	 * br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	private NgListRepresentation genNgList(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = this.genNgAssociatedProject();

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("Strman", Strman.class);
				this.put("StringUtils", StringUtils.class);
				this.put("ngListTemplates", AngularServiceImpl.this.appCtx.getBean(NgListTemplates.class));
				this.put("entity", entity);
				this.put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				this.put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				this.put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("list");
		Path destinationPath = ngModule.getPath().getParent().resolve(entity.getTsFileName());
		Path componentPath = destinationPath.resolve(String.format("%s-list", entity.getTsFileName()));

		this.fs.copy(resourcePath, vars, destinationPath);
		NgListRepresentation ngList = new NgListRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		// this.addComponent(ngModule, ngList);
		this.addImport(ngModule, ngList.getName());

		return ngList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#genNgDetail(br.xtool.core.representation. springboot.EntityRepresentation,
	 * br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	private NgDetailRepresentation genNgDetail(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = this.genNgAssociatedProject();

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("Strman", Strman.class);
				this.put("StringUtils", StringUtils.class);
				this.put("ngDetailTemplates", AngularServiceImpl.this.appCtx.getBean(NgDetailTemplates.class));
				this.put("entity", entity);
				this.put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				this.put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("detail");
		Path destinationPath = ngModule.getPath().getParent().resolve(entity.getTsFileName());
		Path componentPath = destinationPath.resolve(String.format("%s-detail", entity.getTsFileName()));

		this.fs.copy(resourcePath, vars, destinationPath);
		NgDetailRepresentation ngDetail = new NgDetailRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		// this.addComponent(ngModule, ngDetail);
		this.addImport(ngModule, ngDetail.getName());

		return ngDetail;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#genNgEdit(br.xtool.core.representation. springboot.EntityRepresentation,
	 * br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	private NgEditRepresentation genNgEdit(EntityRepresentation entity, NgModuleRepresentation ngModule) {
		NgProjectRepresentation ngProject = this.genNgAssociatedProject();

		String entityFileName = NgClassRepresentation.genFileName(entity.getName()).concat("-edit");
		String entityFolderName = Strman.toKebabCase(entity.getInstanceName());

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("Strman", Strman.class);
				this.put("StringUtils", StringUtils.class);
				this.put("ngEditTemplates", AngularServiceImpl.this.appCtx.getBean(NgEditTemplates.class));
				this.put("entityFileName", entityFileName);
				this.put("entityTsFileName", Strman.toKebabCase(entity.getInstanceName()));
				this.put("entityFolderName", entityFolderName);
				this.put("entityClassName", entity.getName());
				this.put("entity", entity);
				this.put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				this.put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				this.put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("edit");
		Path destinationPath = ngModule.getPath().getParent().resolve(entityFolderName);
		Path componentPath = destinationPath.resolve(String.format("%s-edit", entity.getTsFileName()));

		this.fs.copy(resourcePath, vars, destinationPath);
		NgEditRepresentation ngEdit = new NgEditRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		// this.addComponent(ngModule, ngEdit);
		this.addImport(ngModule, ngEdit.getName());

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

	@Override
	public void printNgEntities(NgProjectRepresentation project) {
		ConsoleLog.print(ConsoleLog.cyan(String.format("-- Entities Angular(%d) --", project.getNgEntities().size())));
		// @formatter:off
		project.getNgEntities()
			.stream()
			.forEach(entity -> ConsoleLog.print(entity.getName()));
		// @formatter:on

	}

	private void addToRoute(NgModuleRepresentation ngModule, NgCrudRepresentation ngCrud) {
		List<NgRoute> ngRoutes = ngModule.getRoutes();
		String rootRoutePath = ngCrud.getList().getPath().getParent().getFileName().toString();
		for (NgRoute r1 : ngRoutes) {
			for (NgRoute r2 : r1.getChildren()) {
				if (r2.getPath().equals(rootRoutePath)) {
					r2.setChildren(ngCrud.genRoute());
					this.updateRoute(ngModule, ngRoutes);
					return;
				}
			}
		}
		NgRoute rootRoute = new NgRoute(rootRoutePath);
		rootRoute.setChildren(ngCrud.genRoute());
		ngRoutes.get(0).getChildren().add(rootRoute);
		this.updateRoute(ngModule, ngRoutes);
	}

	/*
	 * Atualiza o array das rotas.
	 */
	private void updateRoute(NgModuleRepresentation ngModule, List<NgRoute> routes) {
		String content = ngModule.getTsFileContent();
		Pattern pattern = Pattern.compile(NgModuleRepresentation.ROUTE_PATTERN);
		Pair<Integer, Integer> idxRoute = StringHelper.indexOfPattern(pattern, content);
		String startRouteArray = content.substring(idxRoute.getRight());
		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startRouteArray);

		String start = content.substring(0, idxRoute.getRight());
		String end = content.substring(idxRoute.getRight() + idxOfFirstArray.getRight(), content.length());

		// @formatter:off
		PrettyPrinter pp = new DefaultPrettyPrinter()
				.withObjectIndenter(new DefaultIndenter("", " "))
				.withArrayIndenter(new DefaultIndenter())
				.withSpacesInObjectEntries();
		// @formatter:on

		String newContent = start.concat(JsonHelper.serialize(routes, pp).replaceAll("\"", "'")).concat(end);
		this.save(ngModule, newContent);
	}

	private void addComponent(NgModuleRepresentation module, NgCrudRepresentation ngCrud) {
		List<String> declarations = new ArrayList<>(module.getModuleDeclarations());
		declarations.add(ngCrud.getList().getName());
		declarations.add(ngCrud.getDetail().getName());
		ngCrud.getEdit().ifPresent(ngEdit -> declarations.add(ngEdit.getName()));

		String content = module.getTsFileContent();

		Pattern pattern = Pattern.compile(NgModuleRepresentation.DECLARATION_PATTERN);
		int idx = StringHelper.indexOfPattern(pattern, content).getRight();
		String startDeclarationArray = content.substring(idx);
		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startDeclarationArray);
		Pair<Integer, Integer> idxDeclarations = Pair.of(idx + 1, idx + idxOfFirstArray.getRight() - 1);

		String start = content.substring(0, idxDeclarations.getLeft());
		String end = content.substring(idxDeclarations.getRight(), content.length());
		String newContent = start.concat("\n    ").concat(StringUtils.join(declarations, ",\n    ")).concat("\n  ").concat(end);
		this.save(module, newContent);
		return;
	}

	/*
	 *
	 */
	private void addNavigation(NgPageRepresentation page, NgListRepresentation list) {
		List<NgPageNavigationRepresentation> navigations = new ArrayList<>(page.getNavigations());

		NgPageNavigationRepresentation ngPageNavigation = new NgPageNavigationRepresentation();
		ngPageNavigation.setText(list.getName().replace("ListComponent", ""));
		ngPageNavigation.setPath("/" + list.getRoutePath());
		ngPageNavigation.setIcon("file-alt");

		navigations.add(ngPageNavigation);

		String content = page.getTsFileContent();
		Pattern pattern = Pattern.compile(NgPageRepresentation.NAVIGATION_PATTERN);
		int idx = StringHelper.indexOfPattern(pattern, content).getRight();
		String startNavigationArray = content.substring(idx);
		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startNavigationArray);
		Pair<Integer, Integer> idxNavigations = Pair.of(idx, idx + idxOfFirstArray.getRight());

		String start = content.substring(0, idxNavigations.getLeft());
		String end = content.substring(idxNavigations.getRight(), content.length());
		String newContent = start.concat(JsonHelper.serialize(navigations).replaceAll("\"", "'")).concat(end);

		this.save(page, newContent);
		return;

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
			this.save(module, StringUtils.join(lines, "\n"));
		}
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

	@SneakyThrows
	private void save(NgPageRepresentation page, String newContent) {
		try (BufferedWriter writer = Files.newBufferedWriter(page.getPath())) {
			writer.write(newContent);
			writer.flush();
		}
	}
}
