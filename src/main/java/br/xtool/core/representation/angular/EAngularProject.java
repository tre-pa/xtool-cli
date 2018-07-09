package br.xtool.core.representation.angular;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import br.xtool.core.representation.EProject;
import lombok.Getter;

@Getter
public class EAngularProject extends EProject {

	private SortedSet<ENgClass> ngClasses = new TreeSet<>();

	@Getter(lazy = true)
	private final ENgPackage ngPackage = buildNgPackage();

	@Getter(lazy = true)
	private final SortedSet<ENgModule> ngModules = buildNgModules();

	@Getter(lazy = true)
	private final SortedSet<ENgComponent> ngComponents = buildNgComponents();

	@Getter(lazy = true)
	private final SortedSet<ENgService> ngServices = buildNgServices();

	@Getter(lazy = true)
	private final SortedSet<ENgLayout> ngLayouts = buildNgLayouts();

	@Getter(lazy = true)
	private final SortedSet<ENgPage> ngPages = buildNgPages();

	@Getter(lazy = true)
	private final SortedSet<ENgEdit> ngEdits = buildNgEdits();

	@Getter(lazy = true)
	private final SortedSet<ENgDetail> ngDetails = buildNgDetails();

	public EAngularProject(String path, SortedSet<ENgClass> ngClasses) {
		super(path);
		this.ngClasses = ngClasses;
	}

	private ENgPackage buildNgPackage() {
		return ENgPackage.of(FilenameUtils.concat(this.getPath(), "package.json")).orElse(null);
	}

	/**
	 * Retorna as classes modulos do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgModule> buildNgModules() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(".module.ts"))
				.map(ngClass -> new ENgModule(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes components do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgComponent> buildNgComponents() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(".component.ts"))
				.map(ngClass -> new ENgComponent(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes services do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgService> buildNgServices() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(".service.ts"))
				.map(ngClass -> new ENgService(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes layouts do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgLayout> buildNgLayouts() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith("-layout.component.ts"))
				.map(ngClass -> new ENgLayout(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes pages do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgPage> buildNgPages() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith("-page.component.ts"))
				.map(ngClass -> new ENgPage(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes edit do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgEdit> buildNgEdits() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith("-edit.component.ts"))
				.map(ngClass -> new ENgEdit(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes details do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgDetail> buildNgDetails() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith("-detail.component.ts"))
				.map(ngClass -> new ENgDetail(ngClass.getFile()))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}
}
