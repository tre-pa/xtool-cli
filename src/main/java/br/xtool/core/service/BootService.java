package br.xtool.core.service;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootSupport;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaSourceFolder;

/**
 * 
 * @author jcruz
 *
 */
public interface BootService {

	/**
	 * Adicionar um suporte ao projeto.
	 * 
	 * @param bootProject
	 * @param supportClass
	 */
	<T extends BootSupport> void addSupport(EBootProject bootProject, Class<T> supportClass);

	/**
	 * Verifica se o projeto possui suporte.
	 * 
	 * @param bootProject
	 * @param supportClass
	 * @return
	 */
	<T extends BootSupport> boolean hasSupport(EBootProject bootProject, Class<T> supportClass);

	/**
	 * 
	 * @param sourceFolder
	 * @param javaClass
	 */
	void save(EJavaSourceFolder sourceFolder, EJavaClass javaClass);

	//	/**
	//	 * Busca uma classe pelo nome. Caso não encontra retorna um EJavaClass novo.
	//	 * 
	//	 * @param name
	//	 * @return
	//	 */
	//	EJavaClass findJavaClassByName(EBootProject bootProject, String name);

}
