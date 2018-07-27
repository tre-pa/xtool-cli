package br.xtool.core.representation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;

import br.xtool.core.representation.impl.ENgPackageImpl;
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
	@Getter
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
		DIALOG("-dialog.component.ts"),
		ROUTING_MODULE("-routing.module.ts");
		// @formatter:on
		private String ext;

	}

	/**
	 * 
	 * @return
	 */
	Path getAppPath();

	/**
	 * 
	 * @return
	 */
	Path getDomainPath();

	/**
	 * 
	 * @return
	 */
	Path getServicePath();

	/**
	 * 
	 * @return
	 */
	Path getViewPath();

	ENgModule getNgViewModule();

	ENgModule getNgAppModule();

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

	/**
	 * Verifica se o path possui um projeto angular válido.
	 * 
	 * @param path
	 *            Caminho do projeto
	 * @return
	 */
	static boolean isValid(Path path) {
		Path packageJsonFile = path.resolve("package.json");
		if (Files.exists(packageJsonFile)) {
			Optional<ENgPackage> ngPackage = ENgPackageImpl.of(packageJsonFile);
			if (ngPackage.isPresent()) {
				Map<String, String> dependencies = ngPackage.get().getDependencies();
				return dependencies.containsKey("@angular/core");
			}
		}
		return false;
	}
}
