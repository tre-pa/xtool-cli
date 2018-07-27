package br.xtool.core.service;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootSupport;
import br.xtool.core.representation.EJavaClass;

/**
 * 
 * @author jcruz
 *
 */
public interface BootService {

	/**
	 * 
	 * @param bootProject
	 * @param supportClass
	 */
	<T extends BootSupport> void addSupport(EBootProject bootProject, Class<T> supportClass);

	/**
	 * 
	 * @param bootProject
	 * @param supportClass
	 * @return
	 */
	<T extends BootSupport> boolean hasSupport(EBootProject bootProject, Class<T> supportClass);

	/**
	 * Busca uma classe pelo nome. Caso não encontra retorna um EJavaClass novo.
	 * 
	 * @param name
	 * @return
	 */
	EJavaClass findJavaClassByName(EBootProject bootProject, String name);
}
