package br.xtool.core.representation;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma classe no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface PlantClassRepresentation extends  JavaType<JavaClassSource> {

	/** 
	 * Retorna o nome da classe UML.
	 * 
	 * @return
	 */
	@Override
	String getName();

	/**
	 * 
	 * @return
	 */
	String getInstanceName();

	/**
	 * Retorna o nome qualificado. Pacote + Nome.
	 * 
	 * @return
	 */
	@Override
	String getQualifiedName();

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	PlantPackageRepresentation getUmlPackage();
	/**
	 * Retorna o diagrama de classe.
	 * 
	 * @return
	 */
	PlantClassDiagramRepresentation getClassDiagram();

	/**
	 * Retorna os atributos da classe UML.
	 * 
	 * @return
	 */
	Collection<PlantClassFieldRepresentation> getFields();

	/**
	 * Retorna os stereotipos da classe UML.
	 * 
	 * @return
	 */
	Set<PlantStereotypeRepresentation> getStereotypes();

	/**
	 * 
	 * @return
	 */
	Set<PlantRelationshipRepresentation> getRelationships();

	/**
	 * Retorna os attributos chave/valor das notas associadas a classe.
	 * 
	 * @return
	 */
	Map<String, String> getTaggedValues();

	/**
	 * 
	 * @param key
	 * @return
	 */
	Optional<String> getTaggedValue(String key);

	/**
	 * Retorna o valor do tagged value como array.
	 * 
	 * @param key
	 * @return
	 */
	Optional<String[]> getTaggedValueAsArray(String key);

}
