package br.xtool.core.implementation.representation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgDetailRepresentation;
import br.xtool.core.representation.angular.NgEditRepresentation;
import br.xtool.core.representation.angular.NgEntityRepresentation;
import br.xtool.core.representation.angular.NgListRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgPackageJsonRepresentation;
import br.xtool.core.representation.angular.NgPageRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import lombok.Getter;

@Getter
public class NgProjectRepresentationImpl extends ProjectRepresentationImpl implements NgProjectRepresentation {

	private Map<String, NgClassRepresentation> ngClasses;

	public NgProjectRepresentationImpl(Path path) {
		super(path);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.core.representation.ENgProject#getAppPath()
	 */
	@Override
	public Path getAppPath() {
		return getPath().resolve("src/app");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.core.representation.ENgProject#getDomainPath()
	 */
	@Override
	public Path getDomainPath() {
		return getPath().resolve("src/app/domain");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.core.representation.ENgProject#getServicePath()
	 */
	@Override
	public Path getServicePath() {
		return getPath().resolve("src/app/service");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.xtool.core.representation.ENgProject#getViewPath()
	 */
	@Override
	public Path getViewPath() {
		return getPath().resolve("src/app/view");
	}

	@Override
	public SortedSet<NgEntityRepresentation> getNgEntities() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getPath().getParent().endsWith("domain"))
				.map(ngClass -> new NgEntityRepresentationImpl(ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public NgPackageJsonRepresentation getNgPackage() {
		return NgPackageJsonRepresentationImpl.of(getPath().resolve("package.json")).orElse(null);
	}

	@Override
	public NgModuleRepresentation getNgViewModule() {
		// @formatter:off
		return getNgModules().stream()
				.filter(ngModule -> ngModule.getName().equals("ViewModule"))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("O projeto não possui o módulo 'view.module.ts'."));
		// @formatter:on
	}

	@Override
	public NgModuleRepresentation getNgAppModule() {
		// @formatter:off
		return getNgModules().stream()
				.filter(ngModule -> ngModule.getName().equals("AppModule"))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("O projeto não possui o módulo 'app.module.ts'."));
		// @formatter:on
	}

	/**
	 * Retorna as classes modulos do projeto.
	 *
	 * @return
	 */
	@Override
	public SortedSet<NgModuleRepresentation> getNgModules() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getTsFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new NgModuleRepresentationImpl(this, ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes components do projeto.
	 *
	 * @return
	 */
	@Override
	public SortedSet<NgComponentRepresentation> getNgComponents() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getTsFileName().endsWith(NgProjectRepresentation.ArtifactyType.COMPONENT.getExt()))
				.map(ngClass -> new NgComponentRepresentationImpl(ngClass.getPath().getParent()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes services do projeto.
	 *
	 * @return
	 */
	@Override
	public SortedSet<NgServiceRepresentation> getNgServices() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getTsFileName().endsWith(NgProjectRepresentation.ArtifactyType.SERVICE.getExt()))
				.map(ngClass -> new NgServiceRepresentationImpl(ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes pages do projeto.
	 *
	 * @return
	 */
	@Override
	public SortedSet<NgPageRepresentation> getNgPages() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getTsFileName().endsWith(NgProjectRepresentation.ArtifactyType.PAGE.getExt()))
				.map(ngClass -> new NgPageRepresentationImpl(ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public SortedSet<NgListRepresentation> getNgLists() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getTsFileName().endsWith(NgProjectRepresentation.ArtifactyType.LIST.getExt()))
				.map(ngClass -> new NgListRepresentationImpl(ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes edit do projeto.
	 *
	 * @return
	 */
	@Override
	public SortedSet<NgEditRepresentation> getNgEdits() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getTsFileName().endsWith(NgProjectRepresentation.ArtifactyType.EDIT.getExt()))
				.map(ngClass -> new NgEditRepresentationImpl(ngClass.getPath().getParent()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes details do projeto.
	 *
	 * @return
	 */
	@Override
	public SortedSet<NgDetailRepresentation> getNgDetails() {
		// @formatter:off
		return getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getTsFileName().endsWith(NgProjectRepresentation.ArtifactyType.DETAIL.getExt()))
				.map(ngClass -> new NgDetailRepresentationImpl(ngClass.getPath().getParent()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	private Map<String, NgClassRepresentation> getNgClasses() {
		if (Objects.isNull(ngClasses)) {
			// @formatter:off
			ngClasses = listAllFiles().stream()
					.filter(path -> Arrays.asList(ArtifactyType.values()).stream().anyMatch(artifactType -> path.toString().endsWith(artifactType.getExt())))
					.map(NgClassRepresentationImpl::new)
					.collect(Collectors.toMap(ngClass -> ngClass.getPath().toString(), Function.identity()));
			// @formatter:on
		}
		return ngClasses;
	}

	// @Override
	// public String getMainDir() {
	// return FilenameUtils.concat(this.getDirectory().getPath(), "src/app");
	// }

	@Override
	public String getFrameworkVersion() {
		return getNgPackage().getDependencies().get("@angular/core");
	}

	@Override
	public SpringBootProjectRepresentation getTargetSpringBootProject() {
		String springBootPath = getName().endsWith("-frontend") ? getPath().toString().replace("-frontend", "-backend") : getPath().toString().concat("-service");
		if (StringUtils.isNotEmpty(springBootPath)) {
			if (Files.exists(Paths.get(springBootPath))) {
				return new SpringBootProjectRepresentationImpl(Paths.get(springBootPath));
			}
		}
		throw new IllegalArgumentException(String.format("Nenhum projeto Spring Boot associado ao projeto '%s'", getName()));
	}

	@Override
	public Version getProjectVersion() {
		Pattern v5pattern = Pattern.compile("[\\^~]?5\\.2\\.\\d");
		Pattern v6pattern = Pattern.compile("[\\^~]?6\\.\\d\\.\\d");
		Pattern v7pattern = Pattern.compile("[\\^~]?7\\.\\d\\.\\d");
		if (v5pattern.matcher(getFrameworkVersion()).matches()) return Version.V5;
		if (v6pattern.matcher(getFrameworkVersion()).matches()) return Version.V6;
		if (v7pattern.matcher(getFrameworkVersion()).matches()) return Version.V7;
		return Version.NONE;
	}

	@Override
	public void refresh() {
		ngClasses = null;
	}

	@Override
	public Type getProjectType() {
		return ProjectRepresentation.Type.ANGULAR;
	}

	@Override
	public boolean isMultiModule() {
		return false;
	}

}
