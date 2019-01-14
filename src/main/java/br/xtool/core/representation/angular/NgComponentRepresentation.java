package br.xtool.core.representation.angular;

/**
 * Representação de um component angular.
 * 
 * @author jcruz
 *
 */
public interface NgComponentRepresentation extends NgClassRepresentation {

	NgTemplateRepresentation getNgTemplate();
}
