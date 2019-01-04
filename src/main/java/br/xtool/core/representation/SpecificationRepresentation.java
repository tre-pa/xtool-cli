package br.xtool.core.representation;

/**
 * Representação de uma specification JPA.
 * 
 * @author jcruz
 *
 */
public interface SpecificationRepresentation extends JavaClassRepresentation {

	EntityRepresentation getTargetEntity();
}
