package br.xtool.core.representation;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import lombok.Getter;

@Getter
public class EAngularProject extends EProject {

	private SortedSet<ENgClass> ngClasses = new TreeSet<>();

	@Getter(lazy = true)
	private final ENgPackage ngPackage = buildNgPackage();

	@Getter(lazy = true)
	private final SortedSet<ENgClass> ngModules = buildNgModules();

	@Getter(lazy = true)
	private final SortedSet<ENgClass> ngComponents = buildNgComponents();

	@Getter(lazy = true)
	private final SortedSet<ENgClass> ngServices = buildNgServices();

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
	private SortedSet<ENgClass> buildNgModules() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(".module.ts"))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes components do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgClass> buildNgComponents() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(".component.ts"))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Retorna as classes services do projeto.
	 * 
	 * @return
	 */
	private SortedSet<ENgClass> buildNgServices() {
		// @formatter:off
		return ngClasses.stream()
				.filter(ngClass -> ngClass.getFileName().endsWith(".service.ts"))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

}
