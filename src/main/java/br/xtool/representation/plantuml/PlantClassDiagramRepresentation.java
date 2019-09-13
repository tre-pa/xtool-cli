package br.xtool.representation.plantuml;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

/**
 * Representa um diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface PlantClassDiagramRepresentation {

	/**
	 * Retorna o objeto Path do diagrama.
	 * 
	 * @return
	 */
	Path getPath();

	/**
	 * Retorna as classes do diagrama de classe UML.
	 * 
	 * @return
	 */
	Set<PlantClassRepresentation> getClasses();

	/**
	 * Retorna a lista de enums do diagrama de classe UML. Os enums não são associados diretamente as classes estes serão definidos como atributos de classe.
	 * 
	 * @return
	 */
	Set<PlantEnumRepresentation> getEnums();

	/**
	 * Retorna a representação gráfica do diagrama de classe.
	 * 
	 * @return
	 */
	byte[] getPng();

	/**
	 * Retorna a classe do diagrama (se existir) pelo nome.
	 * 
	 * @param name
	 * @return
	 */
	Optional<PlantClassRepresentation> findClassByName(String name);

	// /**
	// * Retorna os relacionamento UML.
	// *
	// * @return
	// */
	// @Deprecated
	// Set<EUmlRelationship> getRelationships();
}
