package br.xtool.core.implementation.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.xtool.core.Clog;
import br.xtool.core.FS;
import br.xtool.core.Shell;
import br.xtool.core.Workspace;
import br.xtool.core.helper.InflectorHelper;
import br.xtool.core.helper.NgHelper;
import br.xtool.core.implementation.representation.NgCrudRepresentationImpl;
import br.xtool.core.implementation.representation.NgDetailRepresentationImpl;
import br.xtool.core.implementation.representation.NgEditRepresentationImpl;
import br.xtool.core.implementation.representation.NgEntityRepresentationImpl;
import br.xtool.core.implementation.representation.NgListRepresentationImpl;
import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgCrudRepresentation;
import br.xtool.core.representation.angular.NgDetailRepresentation;
import br.xtool.core.representation.angular.NgEditRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgListRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.template.angular.NgDetailDxTemplates;
import br.xtool.core.template.angular.NgEditDxTemplates;
import br.xtool.core.template.angular.NgEditTemplate;
import br.xtool.core.template.angular.NgEnumFSTemplate;
import br.xtool.core.template.angular.NgListDxTemplates;
import br.xtool.core.template.angular.NgServiceFSTemplate;
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
		NgProjectRepresentation project = workspace.createProject(
				ProjectRepresentation.Type.ANGULAR,
				version,
				name,
				vars);
		// @formatter:on
		Clog.print(Clog.cyan("\t-- npm install --"));
		shellService.runCmd(project.getPath(), "npm i && code .", vars);
		shellService.runCmd(project.getPath(), "git init > /dev/null 2>&1");
		shellService.runCmd(project.getPath(), "git add . > /dev/null 2>&1");
		shellService.runCmd(project.getPath(), "git commit -m \"Inicial commit\" > /dev/null 2>&1");
		Clog.print(Clog.cyan("\t-- Commit inicial realizado no git. --"));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#createNgEntity(br.xtool.core.representation. springboot.EntityRepresentation)
	 */
	@Override
	public NgEntityRepresentation genNgEntity(NgProjectRepresentation ngProject, EntityRepresentation entity) {
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

		fs.copy(resourcePath, vars, destinationPath);
		Path ngEntityPath = destinationPath.resolve(NgClassRepresentation.genFileName(entity.getName())).resolve(entity.getName().concat(".ts"));
		NgEntityRepresentation ngEntity = new NgEntityRepresentationImpl(ngEntityPath);

		// @formatter:off
		entity.getAttributes()
			.stream()
			.filter(EntityAttributeRepresentation::isEnumField)
			.map(EntityAttributeRepresentation::getEnum)
			.map(Optional::get)
			.forEach(_enum -> genNgEnum(ngProject, _enum));
		// @formatter:on
		return ngEntity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#createNgEnum(br.xtool.core.representation. springboot.JavaEnumRepresentation)
	 */
	@Override
	public void genNgEnum(NgProjectRepresentation ngProject, JavaEnumRepresentation javaEnum) {
//		Map<String, Object> vars = new HashMap<String, Object>() {
//			private static final long serialVersionUID = 1L;
//			{
//				put("Strman", Strman.class);
//				put("javaEnumFileName", NgClassRepresentation.genFileName(javaEnum.getName()));
//				put("javaEnumClassName", javaEnum.getName());
//				put("javaEnum", javaEnum);
//				put("javaEnumConstants", StringUtils.join(javaEnum.getConstants(), ","));
//			}
//		};
//		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("enums");
//		Path destinationPath = ngProject.getNgAppModule().getPath().getParent().resolve("domain").resolve("enums");
//		fs.copy(resourcePath, vars, destinationPath);
//		Path ngEnumPath = destinationPath.resolve(NgClassRepresentation.genFileName(javaEnum.getName())).resolve(javaEnum.getName().concat(".ts"));
//
//		NgEnumRepresentation ngEnum = new NgEnumRepresentationImpl(ngEnumPath);
//		return ngEnum;
		new NgEnumFSTemplate(javaEnum, ngProject).merge(fs);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#genNgService(br.xtool.core.representation. springboot.EntityRepresentation)
	 */
	@Override
	public void genNgService(NgProjectRepresentation ngProject, EntityRepresentation entity) {
		entity.getAssociatedNgEntity().orElseGet(() -> genNgEntity(ngProject, entity));
//
//		Map<String, Object> vars = new HashMap<String, Object>() {
//			private static final long serialVersionUID = 1L;
//			{
//				put("Strman", Strman.class);
//				put("entityFileName", NgClassRepresentation.genFileName(entity.getName()));
//				put("entityClassName", entity.getName());
//				put("entity", entity);
//				put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
//				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
//			}
//		};
//		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("service");
//		Path destinationPath = ngProject.getNgAppModule().getPath().getParent().resolve("service");
//
//		fs.copy(resourcePath, vars, destinationPath);
//		Path ngServicePath = destinationPath.resolve(NgClassRepresentation.genFileName(entity.getName()).concat(".service")).resolve(entity.getName().concat(".ts"));
//		NgServiceRepresentation ngService = new NgServiceRepresentationImpl(ngServicePath);
//
//		return ngService;
		new NgServiceFSTemplate(entity, ngProject).merge(fs);
	}

	@Override
	public NgCrudRepresentation genNgCrud(NgProjectRepresentation ngProject, EntityRepresentation entity, NgModuleRepresentation ngModule) {
		// entity.getAssociatedNgService().orElseGet(() -> genNgService(ngProject, entity));
		if (!entity.getAssociatedNgService().isPresent()) genNgService(ngProject, entity);

		NgListRepresentation ngList = genNgList(ngProject, entity, ngModule);
		NgDetailRepresentation ngDetail = genNgDetail(ngProject, entity, ngModule);
		NgEditRepresentation ngEdit = entity.isImmutable() ? null : genNgEdit(ngProject, entity, ngModule);
		if (Objects.nonNull(ngEdit)) {
			NgCrudRepresentation ngCrud = new NgCrudRepresentationImpl(ngModule, ngList, ngDetail, ngEdit);
			NgHelper.addComponent(ngModule, ngCrud);
			NgHelper.addToRoute(ngModule, ngCrud);
			ngModule.getAssociatedPage().ifPresent(ngPage -> NgHelper.addNavigation(ngPage, ngCrud));
			return ngCrud;
		}
		NgCrudRepresentation ngCrud = new NgCrudRepresentationImpl(ngModule, ngList, ngDetail);
		NgHelper.addComponent(ngModule, ngCrud);
		NgHelper.addToRoute(ngModule, ngCrud);
		ngModule.getAssociatedPage().ifPresent(ngPage -> NgHelper.addNavigation(ngPage, ngCrud));
		return ngCrud;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#genNgList(br.xtool.core.representation. springboot.EntityRepresentation, br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	private NgListRepresentation genNgList(NgProjectRepresentation ngProject, EntityRepresentation entity, NgModuleRepresentation ngModule) {

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("ngListDxTemplates", appCtx.getBean(NgListDxTemplates.class));
				put("entity", entity);
				put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				put("entityApiName", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName())));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("list");
		Path destinationPath = ngModule.getPath().getParent().resolve(entity.getTsFileName());
		Path componentPath = destinationPath.resolve(String.format("%s-list", entity.getTsFileName()));

		fs.copy(resourcePath, vars, destinationPath);
		NgListRepresentation ngList = new NgListRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		// this.addComponent(ngModule, ngList);
		NgHelper.addImport(ngModule, ngList.getName());

		return ngList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#genNgDetail(br.xtool.core.representation. springboot.EntityRepresentation, br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	private NgDetailRepresentation genNgDetail(NgProjectRepresentation ngProject, EntityRepresentation entity, NgModuleRepresentation ngModule) {
		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("ngDetailDxTemplates", appCtx.getBean(NgDetailDxTemplates.class));
				put("entity", entity);
				put("title", InflectorHelper.getInstance().pluralize(entity.getName()));
				put("typescriptTypeMap", NgClassRepresentation.typescriptTypeMap());
			}
		};
		Path resourcePath = Paths.get("angular").resolve(ngProject.getProjectVersion().getName()).resolve("detail");
		Path destinationPath = ngModule.getPath().getParent().resolve(entity.getTsFileName());
		Path componentPath = destinationPath.resolve(String.format("%s-detail", entity.getTsFileName()));

		fs.copy(resourcePath, vars, destinationPath);
		NgDetailRepresentation ngDetail = new NgDetailRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		// this.addComponent(ngModule, ngDetail);
		NgHelper.addImport(ngModule, ngDetail.getName());

		return ngDetail;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.service.AngularService#genNgEdit(br.xtool.core.representation. springboot.EntityRepresentation, br.xtool.core.representation.angular.NgModuleRepresentation)
	 */
	private NgEditRepresentation genNgEdit(NgProjectRepresentation ngProject, EntityRepresentation entity, NgModuleRepresentation ngModule) {
		String entityFileName = NgClassRepresentation.genFileName(entity.getName()).concat("-edit");
		String entityFolderName = Strman.toKebabCase(entity.getInstanceName());

		Map<String, Object> vars = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("Strman", Strman.class);
				put("StringUtils", StringUtils.class);
				put("ngEditDxTemplates", appCtx.getBean(NgEditDxTemplates.class));
				put("ngEditTemplates", appCtx.getBean(NgEditTemplate.class));
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

		fs.copy(resourcePath, vars, destinationPath);
		NgEditRepresentation ngEdit = new NgEditRepresentationImpl(componentPath);

		ngModule.getProject().refresh();

		// this.addComponent(ngModule, ngEdit);
		NgHelper.addImport(ngModule, ngEdit.getName());

		return ngEdit;
	}

	@Override
	public void printNgLists(NgProjectRepresentation project) {
		Clog.print(Clog.cyan(String.format("-- Componentes List(%d) --", project.getNgLists().size())));
		// @formatter:off
		project.getNgLists()
			.stream()
			.forEach(entity -> Clog.print(entity.getName()));
		// @formatter:on
	}

	@Override
	public void printNgEntities(NgProjectRepresentation project) {
		Clog.print(Clog.cyan(String.format("-- Entities Angular(%d) --", project.getNgEntities().size())));
		// @formatter:off
		project.getNgEntities()
			.stream()
			.forEach(entity -> Clog.print(entity.getName()));
		// @formatter:on

	}

//	private void addToRoute(NgModuleRepresentation ngModule, NgCrudRepresentation ngCrud) {
//		List<NgRoute> ngRoutes = ngModule.getRoutes();
//		String rootRoutePath = ngCrud.getTargetEntity().getApiPath();
//		for (NgRoute r1 : ngRoutes) {
//			for (NgRoute r2 : r1.getChildren()) {
//				if (r2.getPath().equals(rootRoutePath)) {
//					r2.setChildren(ngCrud.genRoute());
//					NgHelper.updateRoute(ngModule, ngRoutes);
//					return;
//				}
//			}
//		}
//		NgRoute rootRoute = new NgRoute(rootRoutePath);
//		rootRoute.setChildren(ngCrud.genRoute());
//		ngRoutes.get(0).getChildren().add(rootRoute);
//		NgHelper.updateRoute(ngModule, ngRoutes);
//	}

//	/*
//	 * Atualiza o array das rotas.
//	 */
//	private void updateRoute(NgModuleRepresentation ngModule, List<NgRoute> routes) {
//		String content = ngModule.getTsFileContent();
//		Pattern pattern = Pattern.compile(NgModuleRepresentation.ROUTE_PATTERN);
//		Pair<Integer, Integer> idxRoute = StringHelper.indexOfPattern(pattern, content);
//		String startRouteArray = content.substring(idxRoute.getRight());
//		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startRouteArray);
//
//		String start = content.substring(0, idxRoute.getRight());
//		String end = content.substring(idxRoute.getRight() + idxOfFirstArray.getRight(), content.length());
//
//		// @formatter:off
//		PrettyPrinter pp = new DefaultPrettyPrinter()
//				.withObjectIndenter(new DefaultIndenter("", " "))
//				.withArrayIndenter(new DefaultIndenter())
//				.withSpacesInObjectEntries();
//		// @formatter:on
//
//		String newContent = start.concat(JsonHelper.serialize(routes, pp).replaceAll("\"", "'")).concat(end);
//		this.save(ngModule, newContent);
//	}

//	private void addComponent(NgModuleRepresentation module, NgCrudRepresentation ngCrud) {
//		List<String> declarations = new ArrayList<>(module.getModuleDeclarations());
//		declarations.add(ngCrud.getList().getName());
//		declarations.add(ngCrud.getDetail().getName());
//		ngCrud.getEdit().ifPresent(ngEdit -> declarations.add(ngEdit.getName()));
//
//		String content = module.getTsFileContent();
//
//		Pattern pattern = Pattern.compile(NgModuleRepresentation.DECLARATION_PATTERN);
//		int idx = StringHelper.indexOfPattern(pattern, content).getRight();
//		String startDeclarationArray = content.substring(idx);
//		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startDeclarationArray);
//		Pair<Integer, Integer> idxDeclarations = Pair.of(idx + 1, idx + idxOfFirstArray.getRight() - 1);
//
//		String start = content.substring(0, idxDeclarations.getLeft());
//		String end = content.substring(idxDeclarations.getRight(), content.length());
//		String newContent = start.concat("\n    ").concat(StringUtils.join(declarations, ",\n    ")).concat("\n  ").concat(end);
//		this.save(module, newContent);
//		return;
//	}

	/*
	 *
	 */
//	private void addNavigation(NgPageRepresentation page, NgCrudRepresentation ngCrud) {
//		List<NgPageNavigationRepresentation> navigations = new ArrayList<>(page.getNavigations());
//
//		NgPageNavigationRepresentation ngPageNavigation = new NgPageNavigationRepresentation();
//		ngPageNavigation.setText(ngCrud.getTargetEntity().getName());
//		ngPageNavigation.setPath("/" + ngCrud.getTargetEntity().getApiPath());
//		ngPageNavigation.setIcon("file-alt");
//
//		navigations.add(ngPageNavigation);
//
//		String content = page.getTsFileContent();
//		Pattern pattern = Pattern.compile(NgPageRepresentation.NAVIGATION_PATTERN);
//		int idx = StringHelper.indexOfPattern(pattern, content).getRight();
//		String startNavigationArray = content.substring(idx);
//		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startNavigationArray);
//		Pair<Integer, Integer> idxNavigations = Pair.of(idx, idx + idxOfFirstArray.getRight());
//
//		String start = content.substring(0, idxNavigations.getLeft());
//		String end = content.substring(idxNavigations.getRight(), content.length());
//		String newContent = start.concat(JsonHelper.serialize(navigations).replaceAll("\"", "'")).concat(end);
//
//		this.save(page, newContent);
//		return;
//
//	}
//
//	@SneakyThrows
//	private void addImport(NgModuleRepresentation module, String componentName) {
//		// @formatter:off
//		NgComponentRepresentation component = module.getProject().getNgComponents().stream()
//				.filter(ngC -> ngC.getName().equals(componentName))
//				.findAny()
//				.orElseThrow(() -> new IllegalArgumentException(String.format("NgComponent %s não encontrado.", componentName)));
//
//		if (module.getImports()
//				.stream()
//				.flatMap(ngImport -> ngImport.getItems().stream())
//				.noneMatch(ngImportName -> ngImportName.equals(component.getName()))) {
//			// @formatter:on
//			List<String> lines = Files.readAllLines(module.getPath(), StandardCharsets.UTF_8);
//			Path importPath = module.getPath().getParent().relativize(component.getNgTsClass().getPath());
//			// @formatter:off
//			String importContent = TemplateBuilder.builder()
//					.tpl("import { {{componentName}} } from './{{pathName}}';")
//					.put("componentName", component.getName())
//					.put("pathName", FilenameUtils.removeExtension(importPath.toString()))
//					.build();
//			// @formatter:on
//			lines.add(module.getImports().size(), importContent.trim());
//			this.save(module, StringUtils.join(lines, "\n"));
//		}
//	}

//	@SneakyThrows
//	private void save(NgModuleRepresentation module, String newContent) {
//		try (BufferedWriter writer = Files.newBufferedWriter(module.getPath())) {
//			writer.write(newContent);
//			writer.flush();
//		}
//	}
//
//	@SneakyThrows
//	private void save(NgPageRepresentation page, String newContent) {
//		try (BufferedWriter writer = Files.newBufferedWriter(page.getPath())) {
//			writer.write(newContent);
//			writer.flush();
//		}
//	}
}
