package br.xtool.core.helper;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgCrudRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgPageNavigationRepresentation;
import br.xtool.core.representation.angular.NgPageRepresentation;
import br.xtool.core.representation.angular.NgRoute;
import lombok.SneakyThrows;

/**
 * Classe helper para projetos Angular.
 * 
 * @author jcruz
 *
 */
public class NgHelper {

	/**
	 * Adiciona o componente NgCrud a rota do módulo.
	 * 
	 * @param ngModule
	 * @param ngCrud
	 */
	public static void addToRoute(NgModuleRepresentation ngModule, NgCrudRepresentation ngCrud) {
		List<NgRoute> ngRoutes = ngModule.getRoutes();
		String rootRoutePath = ngCrud.getTargetEntity().getApiPath();
		for (NgRoute r1 : ngRoutes) {
			for (NgRoute r2 : r1.getChildren()) {
				if (r2.getPath().equals(rootRoutePath)) {
					r2.setChildren(ngCrud.genRoute());
					NgHelper.updateRoute(ngModule, ngRoutes);
					return;
				}
			}
		}
		NgRoute rootRoute = new NgRoute(rootRoutePath);
		rootRoute.setChildren(ngCrud.genRoute());
		ngRoutes.get(0).getChildren().add(rootRoute);
		NgHelper.updateRoute(ngModule, ngRoutes);
	}

	private static void updateRoute(NgModuleRepresentation ngModule, List<NgRoute> routes) {
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
		save(ngModule, newContent);
	}

	/**
	 * Adiciona a declaração do componente de crud ao módulo.
	 * 
	 * @param module
	 * @param ngCrud
	 */
	public static void addComponent(NgModuleRepresentation module, NgCrudRepresentation ngCrud) {
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
		save(module, newContent);
	}

	/**
	 * Adicionar o componente de crud ao menu de navegação.
	 * 
	 * @param page
	 * @param ngCrud
	 */
	public static void addNavigation(NgPageRepresentation page, NgCrudRepresentation ngCrud) {
		List<NgPageNavigationRepresentation> navigations = new ArrayList<>(page.getNavigations());

		NgPageNavigationRepresentation ngPageNavigation = new NgPageNavigationRepresentation();
		ngPageNavigation.setText(ngCrud.getTargetEntity().getName());
		ngPageNavigation.setPath("/" + ngCrud.getTargetEntity().getApiPath());
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

		save(page, newContent);
		return;

	}

	/**
	 * Atualiza o import de um módulo.
	 * 
	 * @param module
	 * @param componentName
	 */
	@SneakyThrows
	public static void addImport(NgModuleRepresentation module, String componentName) {
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

	@SneakyThrows
	private static void save(NgModuleRepresentation module, String newContent) {
		try (BufferedWriter writer = Files.newBufferedWriter(module.getPath())) {
			writer.write(newContent);
			writer.flush();
		}
	}

	@SneakyThrows
	private static void save(NgPageRepresentation page, String newContent) {
		try (BufferedWriter writer = Files.newBufferedWriter(page.getPath())) {
			writer.write(newContent);
			writer.flush();
		}
	}
}
