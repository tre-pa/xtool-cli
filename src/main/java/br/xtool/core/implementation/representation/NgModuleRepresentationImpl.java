package br.xtool.core.implementation.representation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Splitter;

import br.xtool.core.helper.JsonHelper;
import br.xtool.core.helper.StringHelper;
import br.xtool.core.representation.angular.NgImportRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgPageRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgRoute;
import lombok.SneakyThrows;

/**
 * Classe que representa um módulo Angular
 * 
 * @author jcruz
 *
 */
public class NgModuleRepresentationImpl extends NgClassRepresentationImpl implements NgModuleRepresentation {

	NgProjectRepresentation ngProject;

//	private Pair<Integer, Integer> idxRoutes = Pair.of(-1, -1);

	private Pair<Integer, Integer> idxDeclarations = Pair.of(-1, -1);

	public NgModuleRepresentationImpl(NgProjectRepresentation ngProject, Path path) {
		super(path);
		this.ngProject = ngProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgModuleRepresentation#getProject()
	 */
	@Override
	public NgProjectRepresentation getProject() {
		return this.ngProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgModuleRepresentation#getImports()
	 */
	@Override
	@SneakyThrows
	public List<NgImportRepresentation> getImports() {
		List<NgImportRepresentation> ngImports = new ArrayList<>();
		List<String> lines = Files.readAllLines(this.getPath());
		for (String line : lines) {
			Pattern pattern = Pattern.compile("import\\s*\\{([\\w\\,\\s]*)\\}\\s*from\\s*'([\\w@\\/\\-\\.]*)");
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				List<String> items = Arrays.asList(matcher.group(1).split("'"));
				String pathName = matcher.group(2);
				ngImports.add(new NgImportImplRepresentation(items, pathName));
			}
		}
		return ngImports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgModuleRepresentation#getRoutes()
	 */
	@Override
	@SneakyThrows
	public List<NgRoute> getRoutes() {
		Pattern pattern = Pattern.compile(NgModuleRepresentation.ROUTE_PATTERN);
		int idx = StringHelper.indexOfPattern(pattern, this.getTsFileContent()).getRight();
		String startRouteArray = this.getTsFileContent().substring(idx);
		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startRouteArray);
		String strRoutes = startRouteArray.substring(idxOfFirstArray.getLeft(), idxOfFirstArray.getRight());

		strRoutes = strRoutes.replaceAll("component\\s*:\\s*(\\w+)", "component: '$1'");
		strRoutes = strRoutes.replaceAll("canActivate\\s*:\\s*\\[(\\w+)\\]", "canActivate: ['$1']");

		return JsonHelper.deserialize(strRoutes, new TypeReference<List<NgRoute>>() {});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgModuleRepresentation# getModuleDeclarations()
	 */
	@Override
	public List<String> getModuleDeclarations() {
		Pattern pattern = Pattern.compile(NgModuleRepresentation.DECLARATION_PATTERN);
		int idx = StringHelper.indexOfPattern(pattern, this.getTsFileContent()).getRight();
		String startDeclarationArray = this.getTsFileContent().substring(idx);
		Pair<Integer, Integer> idxOfFirstArray = StringHelper.indexOfFirstArray(startDeclarationArray);
		String strDeclarationComponents = startDeclarationArray.substring(idxOfFirstArray.getLeft() + 1, idxOfFirstArray.getRight() - 1);
		this.idxDeclarations = Pair.of(idx + 1, idx + idxOfFirstArray.getRight() - 1);
		// @formatter:off
		return Splitter
				.on(",")
				.trimResults()
				.splitToList(strDeclarationComponents);
		// @formatter:on
	}

	@Override
	public Optional<NgPageRepresentation> getAssociatedPage() {
		int idxSuffix = this.getTsFileName().indexOf("-routing.module.ts");
		String modulePreffix = this.getTsFileName().substring(0, idxSuffix);
		// @formatter:off
		return this.getProject().getNgPages()
				.stream()
				.filter(ngPage -> ngPage.getTsFileName().equals(modulePreffix.concat("-page.component.ts")))
				.findAny();
		// @formatter:on
	}

}
