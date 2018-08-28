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
public interface EUmlClass extends EUmlEntity, JavaType<JavaClassSource> {

	/**
	 * Retorna os atributos da classe UML.
	 * 
	 * @return
	 */
	Collection<EUmlField> getFields();

	/**
	 * Retorna os stereotipos da classe UML.
	 * 
	 * @return
	 */
	Set<EUmlStereotype> getStereotypes();

	/**
	 * 
	 * @return
	 */
	Set<EUmlRelationship> getRelationships();

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
