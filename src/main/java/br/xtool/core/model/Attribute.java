package br.xtool.core.model;

import java.util.List;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um atributo de classe.
 * 
 * @author jcruz
 *
 */
public class Attribute implements Comparable<Attribute> {

	private FieldSource<JavaClassSource> fieldSource;

	public Attribute(FieldSource<JavaClassSource> fieldSource) {
		super();
		this.fieldSource = fieldSource;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	public String getName() {
		return this.fieldSource.getName();
	}

	/**
	 * Retorna as annotation do atributo.
	 * 
	 * @return
	 */
	public List<AnnotationSource<JavaClassSource>> getAnnotations() {
		return this.fieldSource.getAnnotations();
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}
	
	@Override
	public int compareTo(Attribute o) {
		return this.getName().compareTo(o.getName());
	}

}
