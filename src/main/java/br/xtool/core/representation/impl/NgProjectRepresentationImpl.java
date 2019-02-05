package br.xtool.core.representation.impl;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.xtool.core.representation.ProjectRepresentation;
import br.xtool.core.representation.angular.NgClassRepresentation;
import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgDetailRepresentation;
import br.xtool.core.representation.angular.NgEditRepresentation;
import br.xtool.core.representation.angular.NgLayoutRepresentation;
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgPackageRepresentation;
import br.xtool.core.representation.angular.NgPageRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.angular.NgServiceRepresentation;
import lombok.Getter;

@Getter
public class NgProjectRepresentationImpl extends EProjectImpl implements NgProjectRepresentation {

	private Map<String, NgClassRepresentation> ngClasses;

	public NgProjectRepresentationImpl(Path path) {
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ENgProject#getAppPath()
	 */
	@Override
	public Path getAppPath() {
		return this.getPath().resolve("src/app");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ENgProject#getDomainPath()
	 */
	@Override
	public Path getDomainPath() {
		return this.getPath().resolve("src/app/domain");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ENgProject#getServicePath()
	 */
	@Override
	public Path getServicePath() {
		return this.getPath().resolve("src/app/service");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ENgProject#getViewPath()
	 */
	@Override
	public Path getViewPath() {
		return this.getPath().resolve("src/app/view");
	}

	@Override
	public NgPackageRepresentation getNgPackage() {
		return ENgPackageImpl.of(this.getPath().resolve("package.json")).orElse(null);
	}

	@Override
	public NgModuleRepresentation getNgViewModule() {
		// @formatter:off
		return this.getNgModules().stream()
				.filter(ngModule -> ngModule.getName().equals("ViewModule"))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("O projeto não possui o módulo 'view.module.ts'."));
		// @formatter:on
	}

	@Override
	public NgModuleRepresentation getNgAppModule() {
		// @formatter:off
		return this.getNgModules().stream()
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
		return this.getNgClasses().values().stream()
				.filter(ngClass -> !ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.ROUTING_MODULE.getExt()))
				.filter(ngClass -> ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new ENgModuleImpl(ngClass.getPath()))
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
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new ENgComponentImpl(ngClass.getPath()))
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
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new ENgServiceImpl(ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes layouts do projeto.
	 * 
	 * @return
	 */
	@Override
	public SortedSet<NgLayoutRepresentation> getNgLayouts() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new ENgLayoutImpl(ngClass.getPath()))
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
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new ENgPageImpl(ngClass.getPath()))
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
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new ENgEditImpl(ngClass.getPath()))
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
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(NgProjectRepresentation.ArtifactyType.MODULE.getExt()))
				.map(ngClass -> new ENgDetailImpl(ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	private Map<String, NgClassRepresentation> getNgClasses() {
		if (Objects.isNull(this.ngClasses)) {
			// @formatter:off
			this.ngClasses = this.listAllFiles().stream()
				.filter(path -> Arrays.asList(ArtifactyType.values()).stream().anyMatch(artifactType -> path.toString().endsWith(artifactType.getExt())))
				.map(ENgClassImpl::new)
				.collect(Collectors.toMap(ngClass -> ngClass.getPath().toString(), Function.identity()));
			// @formatter:on
		}
		return this.ngClasses;
	}

	//	@Override
	//	public String getMainDir() {
	//		return FilenameUtils.concat(this.getDirectory().getPath(), "src/app");
	//	}

	@Override
	public String getFrameworkVersion() {
		return this.getNgPackage().getDependencies().get("@angular/core");
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
		this.ngClasses = null;
	}

	@Override
	public Type getProjectType() {
		return ProjectRepresentation.Type.ANGULAR;
	}

}
