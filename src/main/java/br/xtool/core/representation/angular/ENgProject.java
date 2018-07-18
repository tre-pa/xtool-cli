package br.xtool.core.representation.angular;

import java.util.SortedSet;

import br.xtool.core.representation.EProject;

public interface ENgProject extends EProject {

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

	ENgPackage getNgPackage();

	/**
	 * Retorna as classes modulos do projeto.
	 * 
	 * @return
	 */
	SortedSet<ENgModule> getNgModules();

	/**
	 * Retorna as classes components do projeto.
	 * 
	 * @return
	 */
	SortedSet<ENgComponent> getNgComponents();

	/**
	 * Retorna as classes services do projeto.
	 * 
	 * @return
	 */
	SortedSet<ENgService> getNgServices();

	/**
	 * Retorna as classes layouts do projeto.
	 * 
	 * @return
	 */
	SortedSet<ENgLayout> getNgLayouts();

	/**
	 * Retorna as classes pages do projeto.
	 * 
	 * @return
	 */
	SortedSet<ENgPage> getNgPages();

	/**
	 * Retorna as classes edit do projeto.
	 * 
	 * @return
	 */
	SortedSet<ENgEdit> getNgEdits();

	/**
	 * Retorna as classes details do projeto.
	 * 
	 * @return
	 */
	SortedSet<ENgDetail> getNgDetails();

	@Override
	public String getMainDir();

	@Override
	public void refresh();

}
