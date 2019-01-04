package br.xtool.core.representation;

/**
 * Representação de um component angular.
 * 
 * @author jcruz
 *
 */
public interface NgComponentRepresentation extends NgClassRepresentation {

	NgTemplateRepresentation getNgTemplate();
}
