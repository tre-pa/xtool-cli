package br.xtool.representation.angular;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;

import br.xtool.implementation.representation.NgPackageJsonRepresentationImpl;
import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representação de um projeto Angular.
 *
 * @author jcruz
 *
 */
public interface NgProjectRepresentation extends ProjectRepresentation {

	@AllArgsConstructor
	@Getter
	enum ArtifactyType {
		// @formatter:off
		CLASS(".ts"),
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

	/**
	 * Retorna a referência ao módulo ViewModule.
	 * 
	 * @return
	 */
	NgModuleRepresentation getNgViewModule();

	/**
	 * Retorna a referência ao módulo AppModule
	 * 
	 * @return
	 */
	NgModuleRepresentation getNgAppModule();

	/**
	 * Retorna a repesentação do aquivo package.json
	 * 
	 * @return
	 */
	NgPackageJsonRepresentation getNgPackage();

	/**
	 * Retorna as classes de entidade do projeto.
	 *
	 * @return
	 */
	SortedSet<NgEntityRepresentation> getNgEntities();

	/**
	 * Retorna as classes modulos do projeto.
	 *
	 * @return
	 */
	SortedSet<NgModuleRepresentation> getNgModules();

	/**
	 * Retorna as classes components do projeto.
	 *
	 * @return
	 */
	SortedSet<NgComponentRepresentation> getNgComponents();

	/**
	 * Retorna as classes services do projeto.
	 *
	 * @return
	 */
	SortedSet<NgServiceRepresentation> getNgServices();

	/**
	 * Retorna as classes pages do projeto.
	 *
	 * @return
	 */
	SortedSet<NgPageRepresentation> getNgPages();

	/**
	 * Retorna os componente de lista do projeto.
	 *
	 * @return
	 */
	SortedSet<NgListRepresentation> getNgLists();

	/**
	 * Retorna as classes edit do projeto.
	 *
	 * @return
	 */
	SortedSet<NgEditRepresentation> getNgEdits();

	/**
	 * Retorna as classes details do projeto.
	 *
	 * @return
	 */
	SortedSet<NgDetailRepresentation> getNgDetails();

	// @Override
	// public String getMainDir();

	SpringBootProjectRepresentation getTargetSpringBootProject();

	@Override
	public void refresh();

	/**
	 * Verifica se o path possui um projeto angular válido.
	 *
	 * @param path Caminho do projeto
	 * @return
	 */
	static boolean isValid(Path path) {
		Path packageJsonFile = path.resolve("package.json");
		if (Files.exists(packageJsonFile)) {
			Optional<NgPackageJsonRepresentation> ngPackage = NgPackageJsonRepresentationImpl.of(packageJsonFile);
			if (ngPackage.isPresent()) {
				Map<String, String> dependencies = ngPackage.get().getDependencies();
				return dependencies.containsKey("@angular/core");
			}
		}
		return false;
	}
}
