package br.xtool.core.representation.angular;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.EProject;
import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

@Getter
public class EAngularProject extends EProject {

	private Map<String, ENgClass> ngClasses;

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

	private EAngularProject(String path) {
		super(path);
	}

	public ENgPackage getNgPackage() {
		return ENgPackage.of(FilenameUtils.concat(this.getPath(), "package.json")).orElse(null);
	}

	/**
	 * Retorna as classes modulos do projeto.
	 * 
	 * @return
	 */
	public SortedSet<ENgModule> getNgModules() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.MODULE.ext))
				.map(ngClass -> new ENgModule(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes components do projeto.
	 * 
	 * @return
	 */
	public SortedSet<ENgComponent> getNgComponents() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.COMPONENT.ext))
				.map(ngClass -> new ENgComponent(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes services do projeto.
	 * 
	 * @return
	 */
	public SortedSet<ENgService> getNgServices() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.SERVICE.ext))
				.map(ngClass -> new ENgService(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes layouts do projeto.
	 * 
	 * @return
	 */
	public SortedSet<ENgLayout> getNgLayouts() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.LAYOUT.ext))
				.map(ngClass -> new ENgLayout(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes pages do projeto.
	 * 
	 * @return
	 */
	public SortedSet<ENgPage> getNgPages() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.PAGE.ext))
				.map(ngClass -> new ENgPage(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes edit do projeto.
	 * 
	 * @return
	 */
	public SortedSet<ENgEdit> getNgEdits() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.EDIT.ext))
				.map(ngClass -> new ENgEdit(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes details do projeto.
	 * 
	 * @return
	 */
	public SortedSet<ENgDetail> getNgDetails() {
		// @formatter:off
		return this.getNgClasses().values().stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(ArtifactyType.DETAIL.ext))
				.map(ngClass -> new ENgDetail(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	private Map<String, ENgClass> getNgClasses() {
		if (Objects.isNull(this.ngClasses)) {
			// @formatter:off
			this.ngClasses = this.getDirectory().getAllFiles().stream()
				.filter(file -> Arrays.asList(ArtifactyType.values()).stream().anyMatch(p -> file.getPath().endsWith(p.ext)))
				.map(ENgClass::new)
				.collect(Collectors.toMap(ngClass -> ngClass.getFile().getAbsolutePath(), Function.identity()));
			// @formatter:on
		}
		return this.ngClasses;
	}

	@Override
	public String getMainDir() {
		return FilenameUtils.concat(this.getPath(), "src/app");
	}

	public static Optional<EAngularProject> of(String path) {
		if (Stream.of(ProjectType.ANGULAR5_PROJECT, ProjectType.ANGULAR6_PROJECT).anyMatch(p -> p.equals(EDirectory.of(path).getProjectType()))) {
			return Optional.of(new EAngularProject(path));
		}
		return Optional.empty();
	}

	@Override
	public void onFileCreate(File file) {

	}

	@Override
	public void onFileChange(File file) {

	}

	@Override
	public void onFileDelete(File file) {

	}

}
