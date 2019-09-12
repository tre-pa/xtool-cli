package br.xtool.core.representation.springboot;

/**
 * Representação de uma specification JPA.
 * 
 * @author jcruz
 *
 */
public interface SpringBooSpecificationRepresentation extends JavaClassRepresentation {

	JpaEntityRepresentation getTargetEntity();
}
