package br.xtool.core.representation.impl;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.xtool.core.representation.ENgClass;
import br.xtool.core.representation.ENgComponent;
import br.xtool.core.representation.ENgDetail;
import br.xtool.core.representation.ENgEdit;
import br.xtool.core.representation.ENgLayout;
import br.xtool.core.representation.ENgModule;
import br.xtool.core.representation.ENgPackage;
import br.xtool.core.representation.ENgPage;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.ENgService;
import br.xtool.core.representation.EProject;
import lombok.Getter;

@Getter
public class ENgProjectImpl extends EProjectImpl implements ENgProject {

	private Map<String, ENgClass> ngClasses;

	public ENgProjectImpl(Path path) {
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
	public ENgPackage getNgPackage() {
		return ENgPackageImpl.of(this.getPath().resolve("package.json")).orElse(null);
	}

	/**
	 * Retorna as classes modulos do projeto.
	 * 
	 * @return
	 */
	@Override
	public SortedSet<ENgModule> getNgModules() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.MODULE.ext))
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
	public SortedSet<ENgComponent> getNgComponents() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.COMPONENT.ext))
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
	public SortedSet<ENgService> getNgServices() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.SERVICE.ext))
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
	public SortedSet<ENgLayout> getNgLayouts() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.LAYOUT.ext))
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
	public SortedSet<ENgPage> getNgPages() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.PAGE.ext))
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
	public SortedSet<ENgEdit> getNgEdits() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.EDIT.ext))
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
	public SortedSet<ENgDetail> getNgDetails() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.DETAIL.ext))
				.map(ngClass -> new ENgDetailImpl(ngClass.getPath()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	private Map<String, ENgClass> getNgClasses() {
		if (Objects.isNull(this.ngClasses)) {
			// @formatter:off
			this.ngClasses = this.listAllFiles().stream()
				.filter(path -> Arrays.asList(ArtifactyType.values()).stream().anyMatch(artifactType -> path.toString().endsWith(artifactType.ext)))
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
	public void refresh() {
		this.ngClasses = null;
	}

	@Override
	public Type getProjectType() {
		return EProject.Type.ANGULAR;
	}

	enum ArtifactyType {
		// @formatter:off
		MODULE(".module.ts"),
		COMPONENT(".component.ts"),
		SERVICE("-service.ts"),
		LAYOUT("-layout.component.ts"),
		PAGE("-page.component.ts"),
		EDIT("-edit.component.ts"),
		DETAIL("-detail.component.ts"),
		LIST("-list.component.ts"),
		DIALOG("-dialog.component.ts");
		// @formatter:on
		private String ext;

		private ArtifactyType(String ext) {
			this.ext = ext;
		}

		public String getExt() {
			return this.ext;
		}

	}

}
