package br.xtool.core.service;

import java.util.Set;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootSupport;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaSourceFolder;
import br.xtool.core.visitor.Visitor;

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

	/**
	 * Converte o diagrama de classe UML para as classes correspondentes.
	 * 
	 * @param bootProject
	 * @param umlClass
	 * @return
	 */
	void convertUmlClassDiagramToJavaClasses(EBootProject bootProject, Set<Visitor> vistors);

	//EJavaClass convertUmlClassToJavaClass(EBootProject bootProject, EUmlClass umlClass);
}
