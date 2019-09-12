package br.xtool.core.pdiagram;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;

import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.JpaEntityAttributeRepresentation;

/**
 * 
 * @author jcruz
 *
 */
public interface FieldVisitor {

	/**
	 * 
	 * @param plantField
	 */
	void visit(JpaEntityAttributeRepresentation attribute, PlantClassFieldRepresentation plantField);
	


}
