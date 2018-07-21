package br.xtool.core.representation;

import java.util.SortedSet;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representação de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface ENgProject extends EProject {

	@AllArgsConstructor
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
		@Getter
		private String ext;
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

	//	@Override
	//	public String getMainDir();

	@Override
	public void refresh();

}
