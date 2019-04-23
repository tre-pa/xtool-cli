package br.xtool.core.representation.angular;

/**
 * Representação de um component angular.
 * 
 * @author jcruz
 *
 */
public interface NgComponentRepresentation extends NgClassRepresentation {

	/**
	 * Retorna a caminho na rota.
	 * 
	 * @return
	 */
	String getRoutePath();
	
	/**
	 * 
	 * @return
	 */
	NgRoute getDefaultRoute();

	/**
	 * Retorna o HTML template do component.
	 * 
	 * @return
	 */
	NgHtmlTemplateRepresentation getNgHtmlTemplate();

	/**
	 * Retorna a classe Ts do component.
	 * 
	 * @return
	 */
	NgTsClassRepresentation getNgTsClass();
}
